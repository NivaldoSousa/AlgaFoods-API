package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/*
 * Classe do tipo DTO só que usado para tranferir o valor dos atributos de entrada, como por exemplo
 * salvar um novo restaurante
 * 
 * */
@Getter
@Setter
public class FormaPagamentoInput {

	@NotBlank
	private String descricao;
}
