package com.samuelTI.smartpoint.api.mapper;

import com.samuelTI.smartpoint.api.dtos.LegalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.enums.PerfilEnum;
import com.samuelTI.smartpoint.api.utils.PasswordUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

@Configuration
public class CustomerMapper {

    public Customer convertDtoByCustomer(LegalPersonRegistrationDto cadastroPJDto, BindingResult result) {
        var customer = new Customer();
        customer.setNome(cadastroPJDto.getNome());
        customer.setEmail(cadastroPJDto.getEmail());
        customer.setCpf(cadastroPJDto.getCpf());
        customer.setPerfil(PerfilEnum.ROLE_ADMIN);
        customer.setSenha(PasswordUtils.gerarByCrypt(cadastroPJDto.getSenha()));

        return customer;
    }
}

