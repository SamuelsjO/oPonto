package com.samuelTI.smartpoint.api.services.impl;

import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.repository.CompanyRepository;
import com.samuelTI.smartpoint.api.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	
	@Override
	public Optional<Company> buscarPorCnpj(String cnpj){
		log.info("Find one employee for the CNPJ {}", cnpj);
		return Optional.ofNullable(companyRepository.findByCnpj(cnpj));
	}
	
	@Override
	public Company persitEmpresa(Company company) {
		log.info("Persisting company {}", company);
		return this.companyRepository.save(company);
	}

}
