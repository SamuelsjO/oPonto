package com.samuelTI.smartpoint.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationDto {

	private Long id;
	@NotEmpty(message = "Name can not be empty.")
	@Length(min = 3, max = 200, message = "Name must contain between 3 and 200 characters.")
	private String nome;
	@NotEmpty(message = "Email can not be empty.")
	@Length(min = 5, max = 200, message = "Email must contain between 5 and 200 characters.")
	@Email(message = "Invalid Email")
	private String email;
	private Optional<String> senha = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhadoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();


	@Override
	public String toString() {
		return "CadastroFuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhadoDia=" + qtdHorasTrabalhadoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + "]";
	}

}
