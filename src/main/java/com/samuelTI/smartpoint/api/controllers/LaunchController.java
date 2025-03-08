package com.samuelTI.smartpoint.api.controllers;

import com.samuelTI.smartpoint.api.config.ConfigProperties;
import com.samuelTI.smartpoint.api.dtos.LaunchRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Launch;
import com.samuelTI.smartpoint.api.mapper.LaunchMapper;
import com.samuelTI.smartpoint.api.responses.Response;
import com.samuelTI.smartpoint.api.services.CustomerService;
import com.samuelTI.smartpoint.api.services.LaunchService;
import com.samuelTI.smartpoint.api.utils.Validations;
import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/lancamentos")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LaunchController {

	private final LaunchService launchService;
	private final CustomerService funcService;
	private final LaunchMapper mapper;
	private final ConfigProperties properties;
	private final Validations validations;

	@GetMapping(value = "/funcionario/{funcionarioId}")
	public ResponseEntity<Response<Page<LaunchRegistrationDto>>> listarPorFuncionarioId(
			@PathVariable("funcionarioId") Long funcionarioId, 
			@RequestParam(value = "pag", defaultValue = "0") int pag,
			@RequestParam(value = "ord", defaultValue = "id") String ord,
			@RequestParam(value = "dir", defaultValue = "DESC") String dir) {
		log.info("Find Lancamento bi ID the employee: {}, page: {}", funcionarioId, pag);
		Response<Page<LaunchRegistrationDto>> response = new Response<Page<LaunchRegistrationDto>>();

		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(pag, properties.getQtdPorPagina(), Direction.valueOf(dir), ord);
		Page<Launch> lancaPage = this.launchService.buscarPorFuncionario(funcionarioId, pageRequest);
		Page<LaunchRegistrationDto> lanPageDto = lancaPage.map(mapper::convertLancamentoDto);

		response.setData(lanPageDto);
		return ResponseEntity.ok(response);
	}


	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<LaunchRegistrationDto>> listarPorId(@PathVariable("id") Long id) {
		log.info("Find lancamento by ID: {}", id);
		Response<LaunchRegistrationDto> response = new Response<LaunchRegistrationDto>();
		Optional<Launch> lancamento = this.launchService.buscarById(id);

		if (!lancamento.isPresent()) {
			log.info("Lancamento not found for the ID: {}", id);
			response.getErrors().add("Lancamento not found for the ID " + id);
			return ResponseEntity.ok(response);
		}

		response.setData(mapper.convertLancamentoFindId(lancamento.get()));
		return ResponseEntity.ok(response);
	}

	
	@PostMapping(value="/launch")
	public ResponseEntity<Response<LaunchRegistrationDto>> adicionar(@Valid @RequestBody LaunchRegistrationDto launchRegistrationDto,
																	 BindingResult result) throws ParseException, java.text.ParseException{
		log.info("Adicionando lancamento: {}", launchRegistrationDto.toString());

		final var dates = Timestamp.valueOf(String.valueOf(Timestamp.from(Instant.now()))).toString();
		launchRegistrationDto.setClockTime(dates);
		Response<LaunchRegistrationDto> response = new Response<>();
		validations.validCustomer(launchRegistrationDto,funcService, result);
		Launch launch = mapper.convertDtoByLancamento(launchRegistrationDto, launchService, result);
		
		if(result.hasErrors()){
			log.error("Error validating lancamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
			
		}
		launch = this.launchService.persitir(launch);
		response.setData(mapper.convertLancamentoDto(launch));
		return ResponseEntity.ok(response);
		
	}


	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<LaunchRegistrationDto>> atualizar(@PathVariable("id") Long id,
																	 @Valid @RequestBody LaunchRegistrationDto launchRegistrationDto, BindingResult result)
			throws ParseException, java.text.ParseException {
		
		log.info("Updating lançamento: {}", launchRegistrationDto.toString());
		Response<LaunchRegistrationDto> response = new Response<>();
		validations.validCustomer(launchRegistrationDto, funcService, result);
		launchRegistrationDto.setId(Optional.of(id));
		Launch launch = mapper.convertDtoByLancamento(launchRegistrationDto, launchService, result);
		
		if(result.hasErrors()) {
			log.error("Error validating lançamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		launch = this.launchService.persitir(launch);
		response.setData(mapper.convertLancamentoDto(launch));
		return ResponseEntity.ok(response);

	}

	@DeleteMapping(value = "/{id}")
//	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<String>> remover(@PathVariable("id") Long id){
		log.info("Removing lancamento: {}", id);
		Response<String> response = new Response<>();
		Optional<Launch> lancamento = this.launchService.buscarById(id);
		
		if(!lancamento.isPresent()) {
			log.info("Error removing because of lancamento ID: {} to be invalid.", id);
			response.getErrors().add("Error removing lancamento. Record not found for id" + id);
			return ResponseEntity.badRequest().body(response.getErrors());
		}
		
		this.launchService.remover(id);
		return ResponseEntity.ok(Collections.emptyList());
	}

}
