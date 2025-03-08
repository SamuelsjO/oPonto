package com.samuelTI.smartpoint.api.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.samuelTI.smartpoint.api.entities.Launch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@NamedQueries({
		@NamedQuery(name = "LancamentoRepository.findByFuncionarioId", 
				query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id = :funcionarioId") })
public interface LaunchRepository extends JpaRepository<Launch, Long> {

	/**
	 * Search employee by ID
	 * @param funcionarioId
	 * @return
	 */
	
	List<Launch> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

	
	/**
	 * Search employee with number of pageable page
	 * @param funcionarioId
	 * @param pageable
	 * @return
	 */
	
	Page<Launch> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}

