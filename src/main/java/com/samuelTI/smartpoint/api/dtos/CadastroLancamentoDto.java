package com.samuelTI.smartpoint.api.dtos;

import lombok.*;

import java.util.Optional;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroLancamentoDto {

	private Optional<Long> id = Optional.empty();

	private String data;
	private String tipo;
	private String descricao;
	private String localizacao;
	private Long funcionarioId;


	@Override
	public String toString() {
		return "CadastroLancamentoDto [id=" + id + ", data=" + data + ", tipo=" + tipo + ", descricao=" + descricao
				+ ", localazicao=" + localizacao + ", funcionarioId=" + funcionarioId + "]";
	}

}
