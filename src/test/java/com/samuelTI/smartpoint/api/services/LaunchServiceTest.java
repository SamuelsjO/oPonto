package com.samuelTI.smartpoint.api.services;

import com.samuelTI.smartpoint.api.entities.Launch;
import com.samuelTI.smartpoint.api.repository.LaunchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LaunchServiceTest {

	@MockBean
	private LaunchRepository launchRepository;
	
	@Autowired
	private LaunchService launchService;
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.launchRepository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
		.willReturn(new PageImpl<Launch>(new ArrayList<Launch>()));
		BDDMockito.given(this.launchRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Launch()));
		BDDMockito.given(this.launchRepository.save(Mockito.any(Launch.class))).willReturn(new Launch());
		
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testBuscarLancamentoByFuncioanarioId() {
		Page<Launch> lancamento = this.launchService.buscarPorFuncionario(1L, new PageRequest(0, 10));
		
		assertNotNull(lancamento);
	}
	
	@Test
	public void testBuscarLancamentoById() {
		Optional<Launch> lancamento = this.launchService.buscarById(1L);
		
		assertTrue(lancamento.isPresent());
	}
	
	@Test
	public void testPersitirLancamento() {
		Launch launch = this.launchService.persitir(new Launch());
		
		assertNotNull(launch);
	}
}
