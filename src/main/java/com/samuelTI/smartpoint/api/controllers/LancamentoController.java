package com.samuelTI.smartpoint.api.controllers;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.samuelTI.smartpoint.api.config.ConfigProperties;
import com.samuelTI.smartpoint.api.enums.TipoEnum;
import com.samuelTI.smartpoint.api.mapper.LancamentoMapper;
import com.samuelTI.smartpoint.api.utils.Validations;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelTI.smartpoint.api.dtos.CadastroLancamentoDto;
import com.samuelTI.smartpoint.api.entities.Funcionario;
import com.samuelTI.smartpoint.api.entities.Lancamento;
import com.samuelTI.smartpoint.api.responses.Response;
import com.samuelTI.smartpoint.api.services.FuncionarioService;
import com.samuelTI.smartpoint.api.services.LancamentoService;
import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;

@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LancamentoController {

	private static final Logger log = LoggerFactory.getLogger(LancamentoController.class);
	private final LancamentoService lancamentoService;
	private final FuncionarioService funcService;
	private final LancamentoMapper mapper;
	private final ConfigProperties properties;
	private final Validations validations;

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<CadastroLancamentoDto>>> listarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId, 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Find Lancamento bi ID the employee: {}, page: {}", funcionarioId, pag);
		Response<Page<CadastroLancamentoDto>> response = new Response<Page<CadastroLancamentoDto>>();

		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(pag, properties.getQtdPorPagina(), Direction.valueOf(dir), ord);
		Page<Lancamento> lancaPage = this.lancamentoService.buscarPorFuncionario(funcionarioId, pageRequest);
		Page<CadastroLancamentoDto> lanPageDto = lancaPage.map(lancamento -> mapper.convertLancamentoDto(lancamento));

		response.setData(lanPageDto);
		return ResponseEntity.ok(response);
	}


	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<CadastroLancamentoDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Find lancamento by ID: {}", id);
		Response<CadastroLancamentoDto> response = new Response<CadastroLancamentoDto>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarById(id);

		if (!lancamento.isPresent()) {
			log.info("Lancamento not found for the ID: {}", id);
			response.getErrors().add("Lancamento not found for the ID " + id);
			return ResponseEntity.ok(response);
		}

		response.setData(mapper.convertLancamentoFindId(lancamento.get()));
		return ResponseEntity.ok(response);
	}

	
	@PostMapping(value="/launch")
	public ResponseEntity<Response<CadastroLancamentoDto>> adicionar(@Valid @RequestBody CadastroLancamentoDto cadastroLancamentoDto,
			BindingResult result) throws ParseException, java.text.ParseException{
		log.info("Adicionando lancamento: {}", cadastroLancamentoDto.toString());

		final var dates = Timestamp.valueOf(String.valueOf(Timestamp.from(Instant.now()))).toString();
		cadastroLancamentoDto.setClockTime(dates);
		Response<CadastroLancamentoDto> response = new Response<>();
		validations.validCustomer(cadastroLancamentoDto,funcService, result);
		Lancamento lancamento = mapper.convertDtoByLancamento(cadastroLancamentoDto, lancamentoService, result);
		
		if(result.hasErrors()){
			log.error("Error validating lancamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
			
		}
		lancamento = this.lancamentoService.persitir(lancamento);
		response.setData(mapper.convertLancamentoDto(lancamento));
		return ResponseEntity.ok(response);
		
	}


	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<CadastroLancamentoDto>> atualizar(@PathVariable("id") Long id, 
			@Valid @RequestBody CadastroLancamentoDto cadastroLancamentoDto, BindingResult result)
			throws ParseException, java.text.ParseException {
		
		log.info("Updating lançamento: {}", cadastroLancamentoDto.toString());
		Response<CadastroLancamentoDto> response = new Response<>();
		validations.validCustomer(cadastroLancamentoDto, funcService, result);
		cadastroLancamentoDto.setId(Optional.of(id));
		Lancamento lancamento = mapper.convertDtoByLancamento(cadastroLancamentoDto, lancamentoService, result);
		
		if(result.hasErrors()) {
			log.error("Error validating lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		lancamento = this.lancamentoService.persitir(lancamento);
		response.setData(mapper.convertLancamentoDto(lancamento));
		return ResponseEntity.ok(response);

	}

	@DeleteMapping(value = "/{id}")
//	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<String>> remover(@PathVariable("id") Long id){
		log.info("Removing lancamento: {}", id);
		Response<String> response = new Response<>();
		Optional<Lancamento> lancamento = this.lancamentoService.buscarById(id);
		
		if(!lancamento.isPresent()) {
			log.info("Error removing because of lancamento ID: {} to be invalid.", id);
			response.getErrors().add("Error removing lancamento. Record not found for id" + id);
			return ResponseEntity.badRequest().body(response.getErrors());
		}
		
		this.lancamentoService.remover(id);
		return ResponseEntity.ok().body(response.getData());
	}

}
