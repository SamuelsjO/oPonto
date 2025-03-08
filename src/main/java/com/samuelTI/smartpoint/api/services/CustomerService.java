package com.samuelTI.smartpoint.api.services;

import com.samuelTI.smartpoint.api.entities.Customer;

import java.util.Optional;

public interface CustomerService {

	/**
	 * Persisting one employee in the data base
	 * 
	 * @param customer
	 * @return Funcionario
	 */
	Customer persitirFunc(Customer customer);
	
	/**
	 * Search and return on employee given CPF
	 * 
	 * @param cpf
	 * @return Optional<Funcionario>
	 */
	Optional<Customer> buscarPorCpf(String cpf);
	
	/**
	 * Search and return one employee given a email.
	 * 
	 * @param email
	 * @return Optional<Funcionario>
	 */
	Optional<Customer> buscarPorEmail(String email);
	
	/**
	 * Search and return a employee by ID.
	 * 
	 * @param id
	 * @return Optional<Funcionario>
	 */
	Optional<Customer> buscarPorId(Long id);

	
}
