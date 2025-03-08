package com.samuelTI.smartpoint.api.mapper;

import com.samuelTI.smartpoint.api.dtos.LegalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Customer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegalPersonMapper {

    public LegalPersonRegistrationDto convertRegistrationPJDto(Customer customer) {
        var cadastroPJDto = new LegalPersonRegistrationDto();
        cadastroPJDto.setId(customer.getId());
        cadastroPJDto.setNome(customer.getNome());
        cadastroPJDto.setEmail(customer.getEmail());
        cadastroPJDto.setCpf(customer.getCpf());
        cadastroPJDto.setRazaoSocial(customer.getCompany().getRazaoSocial());
        cadastroPJDto.setCnpj(customer.getCompany().getCnpj());

        return cadastroPJDto;
    }
}
