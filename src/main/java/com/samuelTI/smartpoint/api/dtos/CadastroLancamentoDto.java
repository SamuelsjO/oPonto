package com.samuelTI.smartpoint.api.dtos;

import lombok.*;

import java.util.Optional;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroLancamentoDto {

	private Optional<Long> id = Optional.empty();

	private String clockTime;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;


	@Override
	public String toString() {
		return "CadastroLancamentoDto [id=" + id + ", data=" + clockTime + ", tipo=" + tipo + ", descricao=" + descricao
				+ ", localazicao=" + localizacao + ", funcionarioId=" + funcionarioId + "]";
	}

}
