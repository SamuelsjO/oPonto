package com.samuelTI.smartpoint.api.repository;

import com.samuelTI.smartpoint.api.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	/**
	 * Search employee by cpf
	 * @param cpf
	 * @return
	 */
	Customer findByCpf(String cpf);
	
	/**
	 * Search employee by email
	 * @param email
	 * @return
	 */
	Customer findByEmail(String email);
	
	/**
	 * Search employee by cpf or email
	 * @param cpf
	 * @param email
	 * @return
	 */
	Customer findByCpfOrEmail(String cpf, String email);
}
