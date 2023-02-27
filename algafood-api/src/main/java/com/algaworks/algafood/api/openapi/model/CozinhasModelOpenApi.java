package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* Classe responsável por representar o resultado da consulta da lista de cozinhas por paginação
* */
@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi extends PagedModelOpenApi<CozinhaModel> {

}
