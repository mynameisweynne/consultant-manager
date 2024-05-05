package br.com.brencorp.consman.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.brencorp.consman.entities.Consultor;

@Repository
public interface ConsultorRepository extends JpaRepository<Consultor, Long> {

	List<Consultor> findByNomeContainingIgnoreCase(String nome);

	@Query("SELECT c FROM Consultor c "
		       + "JOIN FETCH c.cidade ci "
		       + "WHERE LOWER(ci.nome) LIKE LOWER(CONCAT('%', :cidade, '%'))")
	List<Consultor> findByCidadeContainingIgnoreCase(String cidade);

	@Query("SELECT c FROM Consultor c " +
            "JOIN FETCH c.cidade ci " +
            "JOIN FETCH ci.estado es " + 
            "WHERE LOWER(es.uf) LIKE LOWER(CONCAT('%', :estado, '%'))")
	List<Consultor> findByEstadoContainingIgnoreCase(String estado);

}
