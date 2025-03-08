package com.samuelTI.smartpoint.api.repository;

import com.samuelTI.smartpoint.api.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface CompanyRepository extends JpaRepository<Company, Long>{

	/**
	 * Search company by cnpj
	 * @param cnpj
	 * @return
	 */
	@Transactional(readOnly = true)
    Company findByCnpj(String cnpj);
}
