package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

/*
 * Classe do tipo DTO só que usado para tranferir o valor dos atributos de entrada, como por exemplo
 * salvar um novo restaurante
 * 
 * */
@Data
public class RestauranteInput {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@PositiveOrZero
	private Double taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaIdInput cozinha;
}
