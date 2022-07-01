package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
/*
 * Essa classe faz o papel do DTO, no caso CozinhaDTO
 * 
 * */
@Data
public class CozinhaModel {

	/*
	 * @JsonView -> Estamos dizendo que o atributo id faz parte da view de Restaurante
	 * */
	@JsonView(RestauranteView.Resumo.class)
	private Long id;

	@JsonView(RestauranteView.Resumo.class)
	private String nome;
}
