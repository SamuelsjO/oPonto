package com.samuelTI.smartpoint.api.controllers;

import com.samuelTI.smartpoint.api.dtos.CustomerRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.responses.Response;
import com.samuelTI.smartpoint.api.services.CustomerService;
import com.samuelTI.smartpoint.api.utils.Validations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class CustomerController {
	private final CustomerService customerService;
	private final Validations validations;

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<CustomerRegistrationDto>> update(@PathVariable("id") Long id,
																	@Valid @RequestBody CustomerRegistrationDto customerRegistrationDto, BindingResult result) {
		log.info("Updgrading employee: {}", customerRegistrationDto.toString());
		Response<CustomerRegistrationDto> response = new Response<>();

		Optional<Customer> funcionario = this.customerService.buscarPorId(id);
		if (!funcionario.isPresent()) {
			result.addError(new ObjectError("funcionario", "Employee not found."));
		}

		validations.updateDataCustomer(funcionario.get(), customerRegistrationDto, customerService, result);

		if (result.hasErrors()) {
			log.error("Error validating employee: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.customerService.persitirFunc(funcionario.get());
		response.setData(validations.convertCustomerDto(funcionario.get()));

		return ResponseEntity.ok(response);
	}

}
