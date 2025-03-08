package com.samuelTI.smartpoint.api.mapper;

import com.samuelTI.smartpoint.api.dtos.CompanyRegistrationDto;
import com.samuelTI.smartpoint.api.dtos.LegalPersonRegistrationDto;
import com.samuelTI.smartpoint.api.entities.Company;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompanyMapper {

    public CompanyRegistrationDto convertCompanyDto(Company company) {

        var empresaDto = new CompanyRegistrationDto();
        empresaDto.setId(company.getId());
        empresaDto.setCnpj(company.getCnpj());
        empresaDto.setRazaoSocial(company.getRazaoSocial());

        return empresaDto;
    }

    public Company convertDtoByCompany(LegalPersonRegistrationDto cadastroPJDto) {
        var company = new Company();
        company.setCnpj(cadastroPJDto.getCnpj());
        company.setRazaoSocial(cadastroPJDto.getRazaoSocial());

        return company;
    }
}

