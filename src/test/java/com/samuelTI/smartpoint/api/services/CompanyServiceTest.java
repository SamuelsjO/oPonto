package com.samuelTI.smartpoint.api.services;

import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CompanyServiceTest {
	
	@MockBean
	private CompanyRepository companyRepository;

	private CompanyService companyService;
	
	private static final String CNPJ = "51463645000100";
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.companyRepository.findByCnpj(Mockito.anyString())).willReturn(new Company());
		BDDMockito.given(this.companyRepository.save(Mockito.any(Company.class))).willReturn(new Company());
		
	}
	
	@Test
	public void testBuscarEmpresaPorCnpj() {
		Optional<Company> empresa = this.companyService.buscarPorCnpj(CNPJ);
		
		assertTrue(empresa.isPresent());
	}
	
	@Test
	public void testPersitirEmpresa() {
		Company company =  this.companyService.persitEmpresa(new Company());
		
		assertNotNull(company);
	}

}
