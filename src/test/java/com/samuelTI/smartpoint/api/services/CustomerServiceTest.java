package com.samuelTI.smartpoint.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import com.samuelTI.smartpoint.api.entities.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.samuelTI.smartpoint.api.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerServiceTest {

	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerService customerService;
	
	private static final String EMAIL_EMPLOYEE = "faculdadesjs@gmail.com";
	private static final String CPF_EMPLOYEE = "10636132641";

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.customerRepository.save(Mockito.any(Customer.class))).willReturn(new Customer());
		BDDMockito.given(this.customerRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Customer()));
		BDDMockito.given(this.customerRepository.findByEmail(Mockito.anyString())).willReturn(new Customer());
		BDDMockito.given(this.customerRepository.findByCpf(Mockito.anyString())).willReturn(new Customer());
	}

	@Test
	public void testPersitirFuncionario() {
		Customer customer = this.customerService.persitirFunc(new Customer());

		assertNotNull(customer);

	}

	@Test
	public void testBuscarFuncionarioPorId() {
		Optional<Customer> funcionario = this.customerService.buscarPorId(1L);

		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Optional<Customer> funcionario = this.customerService.buscarPorEmail(EMAIL_EMPLOYEE);

		assertTrue(funcionario.isPresent());

	}

	@Test
	public void testBuscarFuncionarioPorCpf() {
		Optional<Customer> funcionario = this.customerService.buscarPorCpf(CPF_EMPLOYEE);

		assertTrue(funcionario.isPresent());

	}
}
