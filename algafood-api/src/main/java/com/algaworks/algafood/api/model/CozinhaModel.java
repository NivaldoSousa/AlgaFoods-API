package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/*
 * Essa classe faz o papel do DTO, no caso CozinhaDTO
 * 
 * */
@Data
public class CozinhaModel {

	@JsonView(RestauranteView.Resumo.class) // Estamos dizendo que o atributo id faz parte da view de Restaurante
	@ApiModelProperty(example = "1")
	private Long id;

	@JsonView(RestauranteView.Resumo.class) // Estamos dizendo que o atributo id faz parte da view de Restaurante
	@ApiModelProperty(example = "Brasileira")
	private String nome;
}
