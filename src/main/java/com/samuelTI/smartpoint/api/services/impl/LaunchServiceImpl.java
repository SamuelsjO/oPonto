package com.samuelTI.smartpoint.api.services.impl;

import com.samuelTI.smartpoint.api.entities.Launch;
import com.samuelTI.smartpoint.api.repository.LaunchRepository;
import com.samuelTI.smartpoint.api.services.LaunchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LaunchServiceImpl implements LaunchService {

	private final LaunchRepository launchRepository;
	
	@Override
	public Page<Launch> buscarPorFuncionario(Long funcionarioId, PageRequest pageRequest){
		log.info("Find launch ID: {}", funcionarioId);
		return this.launchRepository.findByFuncionarioId(funcionarioId, pageRequest);
		
	}
	
	@Override
	@Cacheable("launchById")
	public Optional<Launch> buscarById(Long id){
		log.info("Find on launch by ID: {}", id);
		return this.launchRepository.findById(id);
	}
	
	@Override
	@CachePut("launchById")
	public Launch persitir(Launch launch) {
		log.info("Persisting the launch: {}", launch);
		return this.launchRepository.save(launch);
	}
	
	@Override
	public void remover(Long id) {
		log.info("Remove launch ID: {}", id);
		this.launchRepository.deleteById(id);
	}


}
