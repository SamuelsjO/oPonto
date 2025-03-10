package com.samuelTI.smartpoint.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuelTI.smartpoint.api.dtos.CadastroLancamentoDto;
import com.samuelTI.smartpoint.api.entities.Funcionario;
import com.samuelTI.smartpoint.api.entities.Lancamento;
import com.samuelTI.smartpoint.api.enums.TipoEnum;
import com.samuelTI.smartpoint.api.services.FuncionarioService;
import com.samuelTI.smartpoint.api.services.LancamentoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LancamentoControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private LancamentoService lancamentoService;
	
	@MockBean
	private FuncionarioService funcionarioService;
	
	private static final String URL_BASE = "/api/lancamentos/";
	private static final String URL_CAD = "/api/lancamentos/launch";
	private static final Long ID_EMPLOYEE = 1L;
	private static final Long ID_LAUNCH = 1L;
	private static final String TYPE = TipoEnum.START_WORK.name();
	private static final Date DATE = new Date();
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
//	@WithMockUser
	public void testCadastrarLancamento() throws Exception {
		Lancamento lancamento = obterDadosLancamento();
		BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
		BDDMockito.given(this.lancamentoService.persitir(Mockito.any(Lancamento.class))).willReturn(lancamento);

		mvc.perform(MockMvcRequestBuilders.post(URL_CAD)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_LAUNCH))
				.andExpect(jsonPath("$.data.tipo").value(TYPE))
				.andExpect(jsonPath("$.data.clockTime").value(this.dateFormat.format(Date.from(Instant.now()))))
				.andExpect(jsonPath("$.data.funcionarioId").value(ID_EMPLOYEE))
				.andExpect(jsonPath("$.errors").isEmpty());
	}
	
	@Test
//	@WithMockUser
	public void testCadastrarLancamentoFuncionarioIdInvalido() throws Exception {
		BDDMockito.given(this.funcionarioService.buscarPorId(Mockito.anyLong())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.post(URL_CAD)
				.content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Employee not found. ID nonexistent."))
				.andExpect(jsonPath("$.data").isEmpty());
	}
	
	@Test
//	@WithMockUser(username = "admin@admin.com", roles = {"ADMIN"})
	public void testRemoverLancamento() throws Exception {
		BDDMockito.given(this.lancamentoService.buscarById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));

		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LAUNCH)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
//	@Test
////	@WithMockUser
//	public void testRemoverLancamentoAcessoNegado() throws Exception {
//		BDDMockito.given(this.lancamentoService.buscarById(Mockito.anyLong())).willReturn(Optional.of(new Lancamento()));
//
//		mvc.perform(MockMvcRequestBuilders.delete(URL_BASE + ID_LAUNCH)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isForbidden());
//	}

	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		CadastroLancamentoDto cadastrolancamentoDto = new CadastroLancamentoDto();
		cadastrolancamentoDto.setId(null);
		cadastrolancamentoDto.setClockTime(this.dateFormat.format(DATE));
		cadastrolancamentoDto.setTipo(TYPE);
		cadastrolancamentoDto.setFuncionarioId(ID_EMPLOYEE);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(cadastrolancamentoDto);
	}

	private Lancamento obterDadosLancamento() {
		Lancamento lancamento = new Lancamento();
		lancamento.setId(ID_LAUNCH);
		lancamento.setClockTime(DATE);
		lancamento.setTipo(TipoEnum.valueOf(TYPE));
		lancamento.setFuncionario(new Funcionario());
		lancamento.getFuncionario().setId(ID_EMPLOYEE);
		return lancamento;
	}	
	
}
