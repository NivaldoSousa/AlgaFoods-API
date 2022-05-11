package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

/*
 * Essa classe faz o papel do DTO, no caso RestauranteDTO
 * */
@Getter
@Setter
public class EnderecoModel {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeResumoModel cidade;
}
