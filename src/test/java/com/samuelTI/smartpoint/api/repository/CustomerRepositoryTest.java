package com.samuelTI.smartpoint.api.repository;

import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.enums.PerfilEnum;
import com.samuelTI.smartpoint.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CompanyRepository companyRepository;

	private static final String EMAIL = "samucagm@rocketmail.com";
	private static final String CPF = "1063621641";

	@Before
	public void setUp() throws Exception {
		Company company = this.companyRepository.save(obterDadosEmpresa());
		this.customerRepository.save(obterDadosFuncionario(company));
	}

	@After
	public final void tearDown() {
		this.companyRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Customer customer = this.customerRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, customer.getEmail());
	}

	@Test
	public void testBuscarFuncionarioByCpf() {
		Customer customer = this.customerRepository.findByCpf(CPF);

		assertEquals(CPF, customer.getCpf());
	}

	@Test
	public void testBuscarFuncionarioByCpfEmail() {
		Customer customer = this.customerRepository.findByCpfOrEmail(CPF, EMAIL);

		assertNotNull(customer);
	}

	@Test
	public void testBuscarFuncionarioByEmailInvalid() {
		Customer customer = this.customerRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(customer);
	}

	@Test
	public void testBuscarFuncionarioByCpfInvalid() {
		Customer customer = this.customerRepository.findByCpfOrEmail("1234567878", EMAIL);

		assertNotNull(customer);
	}

	private Customer obterDadosFuncionario(Company company) throws NoSuchAlgorithmException {
		Customer customer = new Customer();
		customer.setNome("Samuel");
		customer.setPerfil(PerfilEnum.ROLE_USUARIO);
		customer.setSenha(PasswordUtils.gerarByCrypt("12345"));
		customer.setCpf(CPF);
		customer.setEmail(EMAIL);
		customer.setCompany(company);
		return customer;

	}

	private Company obterDadosEmpresa() {
		Company company = new Company();
		company.setRazaoSocial("SFSTecnologia");
		company.setCnpj("51463645000100");
		return company;

	}
}
