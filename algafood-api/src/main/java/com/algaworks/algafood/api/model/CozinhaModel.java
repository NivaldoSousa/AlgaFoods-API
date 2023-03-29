package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/*
 * Essa classe faz o papel do DTO, no caso CozinhaDTO
 * 
 * */

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

	//@JsonView(RestauranteView.Resumo.class) // Estamos dizendo que o atributo id faz parte da view de Restaurante
	@ApiModelProperty(example = "1")
	private Long id;

	//@JsonView(RestauranteView.Resumo.class) // Estamos dizendo que o atributo id faz parte da view de Restaurante
	@ApiModelProperty(example = "Brasileira")
	private String nome;
}
