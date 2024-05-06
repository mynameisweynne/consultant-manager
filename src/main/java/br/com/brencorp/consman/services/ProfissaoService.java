package br.com.brencorp.consman.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brencorp.consman.dto.ProfissaoDTO;
import br.com.brencorp.consman.entities.Profissao;
import br.com.brencorp.consman.repositories.ProfissaoRepository;
import br.com.brencorp.consman.services.exceptions.DatabaseException;
import br.com.brencorp.consman.services.exceptions.ResourceNotFoundException;
import br.com.brencorp.consman.services.utils.ProfissaoServiceUtil;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfissaoService {

	@Autowired
	private ProfissaoRepository repository;

	@Transactional(readOnly = true)
	public List<ProfissaoDTO> findAll() {
		List<Profissao> profissoes = repository.findAll();
		return profissoes.stream().map(ProfissaoDTO::new).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProfissaoDTO findById(Long id) {
		Profissao profissao = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return new ProfissaoDTO(profissao);
	}
	
	@Transactional(readOnly = true)
	public List<ProfissaoDTO> findByNome(String nome) {
		List<Profissao> profissoes = repository.findByNomeContainingIgnoreCase(nome);
		return profissoes.stream().map(ProfissaoDTO::new).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<ProfissaoDTO> findByArea(String area) {
		List<Profissao> profissoes = repository.findByAreaContainingIgnoreCase(area);
		return profissoes.stream().map(ProfissaoDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public ProfissaoDTO insert(ProfissaoDTO profissaoDTO) {
		Profissao profissao = ProfissaoServiceUtil.insert(profissaoDTO);
		return new ProfissaoDTO(repository.save(profissao));
	}

	@Transactional
	public ProfissaoDTO update(Long id, ProfissaoDTO profissaoDTO) {
		try {
			Profissao profissao = repository.getReferenceById(id);
			ProfissaoServiceUtil.update(profissao, profissaoDTO);
			return new ProfissaoDTO(repository.save(profissao));
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
