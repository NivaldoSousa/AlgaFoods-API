package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;
/*
 * Classe do tipo DTO só que usado para tranferir o valor dos atributos de entrada, como por exemplo
 * salvar um novo restaurante
 * 
 * */
@Data
public class CozinhaIdInput {

	@NotNull
	private Long id;
}
