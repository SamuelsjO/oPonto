package com.samuelTI.smartpoint.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRegistrationDto {
	private Long id;
	private String razaoSocial;
	private String cnpj;


	@Override
	public String toString() {
		return "CadastroEmpresaDto [id=" + id + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}

}
