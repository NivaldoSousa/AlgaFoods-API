package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
/*
 * Classe do tipo DTO só que usado para tranferir o valor dos atributos de entrada, como por exemplo
 * salvar um novo restaurante
 * 
 * */
@Data
public class CozinhaIdInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long id;
}
