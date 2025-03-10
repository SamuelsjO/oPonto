package com.samuelTI.smartpoint.api.entities;

import com.samuelTI.smartpoint.api.enums.TipoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable{

	private static final long serialVersionUID = -5677062704967998133L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "clock_time", nullable = false)
	private Date clockTime;
	
	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@Column(name = "localizacao", nullable = true)
	private String localizacao;
	
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false)
	private TipoEnum tipo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario funcionario;


	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}
	@PrePersist
	public void prePersist() {
		final Date atual = new Date();
		dataCriacao = atual;
		dataAtualizacao = atual;
	}

	@Override
	public String toString() {
		return "Lancamento [data=" + clockTime + ", descricao=" + descricao + ", localizacao=" + localizacao
				+ ", dataCriacao=" + dataCriacao + ", dataAtualizacao=" + dataAtualizacao + ", tipo=" + tipo
				+ ", funcionario=" + funcionario + "]";
	}
	
	
}
