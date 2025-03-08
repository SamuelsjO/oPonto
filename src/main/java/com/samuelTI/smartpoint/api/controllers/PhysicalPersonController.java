
package com.samuelTI.smartpoint.api.controllers;

import com.samuelTI.smartpoint.api.dtos.PhysicalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.mapper.PhysicalPersonMapper;
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
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cadastra-pf")
@CrossOrigin(origins = "*")
public class PhysicalPersonController {
	private final CompanyService companyService;
	private final CustomerService customerService;
	private final PhysicalPersonMapper physicalPersonMapper;
	private final Validations validations;

	@PostMapping
	public ResponseEntity<Response<PhysicalPersonRegistrationDto>> registration(@Valid @RequestBody PhysicalPersonRegistrationDto cadastroPFDto,
																				BindingResult result) {
		log.info("PF registering: {}", cadastroPFDto.toString());
		Response<PhysicalPersonRegistrationDto> response = new Response<>();

		validations.validateExistingPJData(cadastroPFDto, companyService, customerService,result);
		Customer customer = physicalPersonMapper.convertDtoByCustomer(cadastroPFDto, result);

		if (result.hasErrors()) {
			log.info("Error validating registration data of PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Company> empresa = this.companyService.buscarPorCnpj(cadastroPFDto.getCnpj());
		empresa.ifPresent(customer::setCompany);
		this.customerService.persitirFunc(customer);

		response.setData(physicalPersonMapper.convertRegistrationPFDto(customer));
		return ResponseEntity.ok(response);
	}

}
