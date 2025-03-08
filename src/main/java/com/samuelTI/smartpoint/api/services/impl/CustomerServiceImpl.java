package com.samuelTI.smartpoint.api.services.impl;

import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.repository.CustomerRepository;
import com.samuelTI.smartpoint.api.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	
	@Override
	public Customer persitirFunc(Customer customer) {
		log.info("Persisting employee: {}", customer);
		return this.customerRepository.save(customer);
	}
	
	@Override
	public Optional<Customer> buscarPorCpf(String cpf) {
		log.info("Find employee by cpf{}", cpf);
		return Optional.ofNullable(this.customerRepository.findByCpf(cpf));
	}
	
	@Override
	public Optional<Customer> buscarPorEmail(String email) {
		log.info("Find employee by email {}", email);
		return Optional.ofNullable(this.customerRepository.findByEmail(email));
		
	}
	
	@Override
	public Optional<Customer> buscarPorId(Long id){
		log.info("Find employee by id {}", id);
		return this.customerRepository.findById(id);
	}
}
