package com.samuelTI.smartpoint.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LegalPersonRegistrationDto {

	private Long id;
	@NotEmpty(message = "Name can not be empty")
	@Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
	private String nome;
	@NotEmpty(message = "Email can not be empty.")
	@Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
	private String email;
	@NotEmpty(message = "Password can not be empty")
	private String senha;
	@NotEmpty(message = "Social reason can not be empty.")
	@Length(min = 5, max = 200, message = "Social reason must contain between 5 and 200 characteres.")
	private String razaoSocial;
	@NotEmpty(message = "CNPJ can not be empty.")
	@CNPJ(message = "Invalid CNPJ.")
	private String cnpj;
	@NotEmpty(message = "CPF can not be empty.")
	@CPF(message = "Invalid CPF.")
	private String cpf;


	@Override
	public String toString() {
		return "CadastroPJDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", razaoSocial="
				+ razaoSocial + ", cnpj=" + cnpj + "]";
	}
	

}
