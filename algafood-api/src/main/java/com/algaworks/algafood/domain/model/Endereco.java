package com.algaworks.algafood.domain.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Embeddable // E uma classe incoporavel a uma entidade
public class Endereco {

	@Column(name = "endereco_cep")
	private String cep;

	@Column(name = "endereco_logradouro")
	private String logradouro;

	@Column(name = "endereco_numero")
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@Column(name = "endereco_bairro")
	private String bairro;

	@ManyToOne(fetch = FetchType.LAZY)//Busca somente se o model da entidade fazer o GET nessa entidade Cidade
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;
}
