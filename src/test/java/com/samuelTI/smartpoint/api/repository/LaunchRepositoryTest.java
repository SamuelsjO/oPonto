package com.samuelTI.smartpoint.api.repository;

import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.entities.Launch;
import com.samuelTI.smartpoint.api.enums.PerfilEnum;
import com.samuelTI.smartpoint.api.enums.TipoEnum;
import com.samuelTI.smartpoint.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LaunchRepositoryTest {

	@Autowired
	private LaunchRepository launchRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired

	private CompanyRepository companyRepository;

	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {
		
		Company company = this.companyRepository.save(obterDadosEmpresa());
		
		Customer customer = this.customerRepository.save(obterDadosFuncionario(company));
		this.funcionarioId = customer.getId();
		
		this.launchRepository.save(obterDadosLancamentos(customer));
		this.launchRepository.save(obterDadosLancamentos(customer));
	}

	@After
	public void tearDown() throws Exception{
		this.companyRepository.deleteAll();
		
	}
	
	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Launch> launches = this.launchRepository.findByFuncionarioId(funcionarioId);
		
		assertEquals(2, launches.size());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Launch> lancamentos = this.launchRepository.findByFuncionarioId(funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	private Launch obterDadosLancamentos(Customer customer) {
		Launch launch = new Launch();
		launch.setClockTime(new Date());
		launch.setTipo(TipoEnum.START_LUNCH);
		launch.setCustomer(customer);
		return launch;
	}

	private Customer obterDadosFuncionario(Company company) throws NoSuchAlgorithmException{
		Customer customer = new Customer();
		customer.setNome("Samuel");
		customer.setPerfil(PerfilEnum.ROLE_USUARIO);
		customer.setSenha(PasswordUtils.gerarByCrypt("12345"));
		customer.setCpf("10636132641");
		customer.setEmail("samucagm@rockemail.com");
		customer.setCompany(company);

		return customer;
	}

	private Company obterDadosEmpresa() {
		Company company = new Company();
		company.setRazaoSocial("SFTecnologia");
		company.setCnpj("514636450000100");
		return company;
	}

}
