package com.samuelTI.smartpoint.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "empresa")
public class Company implements Serializable{

	private static final long serialVersionUID = 3500465487414732917L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "razao_social", nullable = false)
	private String razaoSocial;
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	@Column(name = "data_criacao", nullable = false)
	private Date dataCriacao;
	@Column(name = "data_atualizacao", nullable = false)
	private Date dataAtualizacao;
	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Customer> customers;

	
	@PreUpdate
	public void preUpdate() {
		dataAtualizacao = new Date();
	}
	
	@PrePersist
	public void prePersit() {
		final Date atual = new Date();
		dataCriacao = atual;
		dataAtualizacao = atual;
	}
	@Override
	public String toString() {
		
		return "Empresa [id=" + id + ", razaoSocial=" + razaoSocial + " , cnpj=" + cnpj + " , dataCriacao=" + dataCriacao + ""
				+ ", dataAtualizacao=" + dataAtualizacao + "]";
	}
	
	
}
