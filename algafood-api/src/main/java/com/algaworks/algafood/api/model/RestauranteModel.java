package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
/*
 * Essa classe faz o papel do DTO, no caso RestauranteDTO
 * */
@Getter
@Setter
public class RestauranteModel {

	/*
	* @JsonView -> Estamos dizendo que o atributo id faz parte da view de Restaurante
	* */
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private Long id;

	/*
	* @JsonView -> Estamos dizendo que o atributo nome pode ser representado por duas ou mais view
	* basta usar @JsonView({}) pois seu value aceita um array
	* */
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;

	@JsonView(RestauranteView.Resumo.class)
	private Double taxaFrete;

	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;

	private Boolean ativo;

	private EnderecoModel endereco;

	private Boolean aberto;
}
