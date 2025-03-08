package com.samuelTI.smartpoint.api.services;

import java.util.Optional;

import com.samuelTI.smartpoint.api.entities.Launch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface LaunchService {

	/**
	 * Search launch by employee
	 * @param funcionarioId
	 * @param pageRequest
	 * @return
	 */
	Page<Launch> buscarPorFuncionario(Long funcionarioId, PageRequest pageRequest);

	/**
	 * Persisting launch
	 * @param launch
	 * @return
	 */
	Launch persitir(Launch launch);

	/**
	 * Remove launch
	 * @param id
	 */
	void remover(Long id);

	/**
	 * Find launch by ID
	 * @param id
	 * @return
	 */
	Optional<Launch> buscarById(Long id);

}
