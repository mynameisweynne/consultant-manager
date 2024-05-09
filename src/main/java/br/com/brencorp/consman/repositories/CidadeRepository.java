package br.com.brencorp.consman.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brencorp.consman.entities.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	@Query("SELECT ci FROM Cidade ci " +
			"WHERE LOWER(ci.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
	List<Cidade> findByNomeContainingIgnoreCase(String nome);
	
	@Query("SELECT ci FROM Cidade ci " +
            "JOIN FETCH ci.estado es " + 
            "WHERE LOWER(es.uf) LIKE LOWER(CONCAT('%', :estado, '%'))")
	List<Cidade> findByEstadoContainingIgnoreCase(String estado);

}
