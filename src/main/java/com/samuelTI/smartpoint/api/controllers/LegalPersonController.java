package com.samuelTI.smartpoint.api.controllers;

import com.samuelTI.smartpoint.api.dtos.LegalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.mapper.CompanyMapper;
import com.samuelTI.smartpoint.api.mapper.CustomerMapper;
import com.samuelTI.smartpoint.api.mapper.LegalPersonMapper;
import com.samuelTI.smartpoint.api.responses.Response;
import com.samuelTI.smartpoint.api.services.CompanyService;
import com.samuelTI.smartpoint.api.services.CustomerService;
import com.samuelTI.smartpoint.api.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/cadastra-pj")
@CrossOrigin(origins = "*")
public class LegalPersonController {

	private final CustomerService customerService;
	private final CompanyService companyService;
	private final Validations validations;
	private final CustomerMapper customerMapper;
	private final LegalPersonMapper personMapper;
	private final CompanyMapper companyMapper;
	
	@PostMapping
	public ResponseEntity<Response<LegalPersonRegistrationDto>> registration(@Valid @RequestBody LegalPersonRegistrationDto cadastroPJDto,
																			 BindingResult result) {
		log.info("PJ registering: {}", cadastroPJDto.toString());
		Response<LegalPersonRegistrationDto> response = new Response<LegalPersonRegistrationDto>();

		validations.validateExistingPfData(cadastroPJDto, companyService, customerService, result);
		Company company = companyMapper.convertDtoByCompany(cadastroPJDto);
		Customer customer = customerMapper.convertDtoByCustomer(cadastroPJDto, result);
		
		if(result.hasErrors()) {
			log.error("Error validating registration data of PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.companyService.persitEmpresa(company);
		customer.setCompany(company);
		this.customerService.persitirFunc(customer);
		
		response.setData(personMapper.convertRegistrationPJDto(customer));
		return ResponseEntity.ok(response);
	}

}
