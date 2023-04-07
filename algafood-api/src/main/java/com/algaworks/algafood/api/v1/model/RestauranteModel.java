package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/*
 * Essa classe faz o papel do DTO, no caso RestauranteDTO
 * */
@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

	@ApiModelProperty(example = "1")
	//@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class}) // @JsonView -> Estamos dizendo que o atributo id faz parte da view de Restaurante
	private Long id;

	@ApiModelProperty(example = "Thai Gourmet")
	//@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class}) // @JsonView -> Estamos dizendo que o atributo nome pode ser representado por duas ou mais view, basta usar @JsonView({}) pois seu value aceita um array
	private String nome;

	@ApiModelProperty(example = "12.00")
	//@JsonView(RestauranteView.Resumo.class)
	private Double taxaFrete;

	//@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;

	private Boolean ativo;

	private EnderecoModel endereco;

	private Boolean aberto;
}
