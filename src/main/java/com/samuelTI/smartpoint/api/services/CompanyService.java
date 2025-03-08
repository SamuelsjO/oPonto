package com.samuelTI.smartpoint.api.services;

import com.samuelTI.smartpoint.api.entities.Company;

import java.util.Optional;

public interface CompanyService {

	/**
	 * Return one company by CNPJ
	 * 
	 * @param cnpj
	 * @return Optional<Empresa>
	 */
	
	Optional<Company> buscarPorCnpj(String cnpj);
		/**
		 * Registered one new company in the data base
		 * 
		 * @param company
		 * @return Empresa
		 * 
		 */
		
	Company persitEmpresa(Company company);
	
}
