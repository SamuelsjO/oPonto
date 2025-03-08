package com.samuelTI.smartpoint.api.controllers;

import com.samuelTI.smartpoint.api.dtos.CompanyRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.mapper.CompanyMapper;
import com.samuelTI.smartpoint.api.responses.Response;
import com.samuelTI.smartpoint.api.services.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class CompanyController {

	private final CompanyService companyService;
	private final CompanyMapper mapper;

	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<CompanyRegistrationDto>> findByCnpj(@PathVariable("cnpj") String cnpj) {
		log.info("Find company by CNPJ: {}", cnpj);
		Response<CompanyRegistrationDto> response = new Response<>();
		Optional<Company> empresa = companyService.buscarPorCnpj(cnpj);

		if (!empresa.isPresent()) {
			log.info("Company not found for the CNPJ" + cnpj);
			response.getErrors().add("Company not found for the CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(response);

		}

		response.setData(mapper.convertCompanyDto(empresa.get()));
		return ResponseEntity.ok(response);
	}
}
