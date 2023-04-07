package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/*
 * Classe do tipo DTO só que usado para tranferir o valor dos atributos de entrada, como por exemplo
 * salvar um novo restaurante
 * 
 * */
@Data
public class RestauranteInput {

	@ApiModelProperty(example = "Thai Gourmet", required = true)
	@NotBlank
	private String nome;

	@ApiModelProperty(example = "12.00", required = true)
	@NotNull
	@PositiveOrZero
	private Double taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;

	@Valid
	@NotNull
	private EnderecoInput endereco;
}
