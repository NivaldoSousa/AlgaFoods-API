package com.algaworks.algafood.api.model;

import lombok.Data;
/*
 * Essa classe faz o papel do DTO, no caso RestauranteDTO
 * */
@Data
public class RestauranteModel {

	private Long id;
	private String nome;
	private Double taxaFrete;
	private CozinhaModel cozinha;
}
