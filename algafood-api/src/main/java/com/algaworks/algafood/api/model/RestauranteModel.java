package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
/*
 * Essa classe faz o papel do DTO, no caso RestauranteDTO
 * */
@Getter
@Setter
public class RestauranteModel {

	private Long id;
	private String nome;
	private Double taxaFrete;
	private CozinhaModel cozinha;
	private Boolean ativo;
	private EnderecoModel endereco;
}
