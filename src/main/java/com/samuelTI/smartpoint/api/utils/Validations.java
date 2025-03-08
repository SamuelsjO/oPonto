package com.samuelTI.smartpoint.api.utils;

import com.samuelTI.smartpoint.api.dtos.CustomerRegistrationDto;
import com.samuelTI.smartpoint.api.dtos.LaunchRegistrationDto;
import com.samuelTI.smartpoint.api.dtos.LegalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.dtos.PhysicalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Company;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.services.CompanyService;
import com.samuelTI.smartpoint.api.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Configuration
public class Validations {

    public void validCustomer(LaunchRegistrationDto launchRegistrationDto, CustomerService service, BindingResult result) {
        if (launchRegistrationDto.getFuncionarioId() == null) {
            result.addError(new ObjectError("Employee", "Employee not found"));
            return;
        }

        log.info("Validing employee id {}: ", launchRegistrationDto.getFuncionarioId());
        Optional<Customer> funcionario = service.buscarPorId(launchRegistrationDto.getFuncionarioId());
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("Employee", "Employee not found. ID nonexistent."));
        }
    }

    public void updateDataCustomer(Customer customer, CustomerRegistrationDto customerRegistrationDto, CustomerService service,
                                   BindingResult result) {
        customer.setNome(customerRegistrationDto.getNome());

        if (!customer.getEmail().equals(customerRegistrationDto.getEmail())) {
            service.buscarPorEmail(customerRegistrationDto.getEmail())
                    .ifPresent(func -> result.addError(new ObjectError("Email", "Existing Email.")));

            customer.setEmail(customerRegistrationDto.getEmail());
        }

        customer.setQtdHorasAlmoco(null);
        customerRegistrationDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasHorasAlmoco -> customer.setQtdHorasAlmoco(Float.valueOf(qtdHorasHorasAlmoco)));

        customer.setQtdHorasTrabalhadas_dia(null);
        customerRegistrationDto.getQtdHorasTrabalhadoDia().ifPresent(qtdHorasTrabDia -> customer.setQtdHorasTrabalhadas_dia(Float.valueOf(qtdHorasTrabDia)));

        customer.setValorHora(null);
        customerRegistrationDto.getValorHora().ifPresent(valorHora -> customer.setValorHora(new BigDecimal(valorHora)));

        if(customerRegistrationDto.getSenha().isPresent()) {
            customer.setSenha(PasswordUtils.gerarByCrypt(customerRegistrationDto.getSenha().get()));

        }

    }

    public CustomerRegistrationDto convertCustomerDto(Customer customer) {
        CustomerRegistrationDto customerRegistrationDto = new CustomerRegistrationDto();
        customerRegistrationDto.setId(customer.getId());
        customerRegistrationDto.setEmail(customer.getEmail());
        customerRegistrationDto.setNome(customer.getNome());
        customer.getQtdHorasAlmocoOpt().ifPresent(
                qtdHorasAlmoco -> customerRegistrationDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));

        customer.getQtdHorasTrabalhoDiaOpt().ifPresent(
                qtdHorasTrabDia -> customerRegistrationDto.setQtdHorasTrabalhadoDia(Optional.of(Float.toString(qtdHorasTrabDia))));

        customer.getValorHoraOpt().ifPresent(
                valorHora -> customerRegistrationDto.setValorHora(Optional.of(valorHora.toString())));

        return customerRegistrationDto;

    }

    public void validateExistingPfData(LegalPersonRegistrationDto cadastroPJDto, CompanyService companyService, CustomerService customerService, BindingResult result) {

        companyService.buscarPorCnpj(cadastroPJDto.getCnpj())
                .ifPresent(emp -> result.addError(new ObjectError("empresa", "Existing Company")));
        customerService.buscarPorCpf(cadastroPJDto.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("Funcionario", "Existing CPF")));
        customerService.buscarPorEmail(cadastroPJDto.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("Funcionario", "Existing Email")));

    }


    public void validateExistingPJData(PhysicalPersonRegistrationDto cadastroPFDto, CompanyService companyService,
                                        CustomerService customerService, BindingResult result) {
        Optional<Company> company = companyService.buscarPorCnpj(cadastroPFDto.getCnpj());
        if (!company.isPresent()) {
            result.addError(new ObjectError("empresa", "Company not registered não cadastrada"));
        }

        customerService.buscarPorCpf(cadastroPFDto.getCpf())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Existing CPF")));

        customerService.buscarPorEmail(cadastroPFDto.getEmail())
                .ifPresent(func -> result.addError(new ObjectError("funcionario", "Existing Email.")));

    }
}
