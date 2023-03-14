package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/*
 * RepresentationModel é um container para coleção de links, onde existe metodos de adições de links
 *
 * */
@Setter
@Getter
public class EstadoModel extends RepresentationModel<EstadoModel> {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Rio de Janeiro")
	private String nome;
}
