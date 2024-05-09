package br.com.brencorp.consman.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.brencorp.consman.dto.ProjetoDTO;
import br.com.brencorp.consman.services.ProjetoService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(value = "/projetos")
public class ProjetoController {

	private final ProjetoService service;

	@Autowired
	public ProjetoController(ProjetoService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<ProjetoDTO>> findAll() {
		List<ProjetoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProjetoDTO> findById(@PathVariable Long id) {
		ProjetoDTO projetoDTO = service.findById(id);
		return ResponseEntity.ok(projetoDTO);
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<List<ProjetoDTO>> find(
			@RequestParam(value = "nome", required = false) String nome) {
		if (nome != null) {
			List<ProjetoDTO> list = service.findByNome(nome);
			return ResponseEntity.ok(list);
		} else {
			List<ProjetoDTO> list = service.findAll();
			return ResponseEntity.ok().body(list);
		}
	}

	@PostMapping
	public ResponseEntity<ProjetoDTO> insert(@RequestBody ProjetoDTO projetoDTO) {
		projetoDTO = service.insert(projetoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(projetoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(projetoDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProjetoDTO> update(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
		projetoDTO = service.update(id, projetoDTO);
		return ResponseEntity.ok().body(projetoDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}