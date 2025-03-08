package com.samuelTI.smartpoint.api.mapper;

import com.samuelTI.smartpoint.api.dtos.PhysicalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Customer;
import com.samuelTI.smartpoint.api.enums.PerfilEnum;
import com.samuelTI.smartpoint.api.utils.PasswordUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class PhysicalPersonMapper {

    public Customer convertDtoByCustomer(@Valid PhysicalPersonRegistrationDto cadastroPFDto, BindingResult result) {
        var customer = new Customer();
        customer.setNome(cadastroPFDto.getNome());
        customer.setEmail(cadastroPFDto.getEmail());
        customer.setCpf(cadastroPFDto.getCpf());
        customer.setPerfil(PerfilEnum.ROLE_USUARIO);
        customer.setSenha(PasswordUtils.gerarByCrypt(cadastroPFDto.getSenha()));
        cadastroPFDto.getQtdHorasAlmoco()
                .ifPresent(qtdHorasAlmoco -> customer.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
        cadastroPFDto.getQtdHorasTrabalhoDia()
                .ifPresent(qtdHorasTrabDia -> customer.setQtdHorasTrabalhadas_dia(Float.valueOf(qtdHorasTrabDia)));
        cadastroPFDto.getValorHora().ifPresent(valorHora -> customer.setValorHora(new BigDecimal(valorHora)));

        return customer;
    }

    public PhysicalPersonRegistrationDto convertRegistrationPFDto(Customer customer) {

        var cadastroPFDto = new PhysicalPersonRegistrationDto();
        cadastroPFDto.setId(customer.getId());
        cadastroPFDto.setNome(customer.getNome());
        cadastroPFDto.setEmail(customer.getEmail());
        cadastroPFDto.setCpf(customer.getCpf());
        cadastroPFDto.setCnpj(customer.getCpf());

        customer.getQtdHorasAlmocoOpt().ifPresent(
                qtdHorasAlmoco -> cadastroPFDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));

        customer.getQtdHorasTrabalhoDiaOpt().ifPresent(
                qtdHorasTrabDia -> cadastroPFDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));

        customer.getValorHoraOpt()
                .ifPresent(valorHora -> cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));

        return cadastroPFDto;
    }
}
