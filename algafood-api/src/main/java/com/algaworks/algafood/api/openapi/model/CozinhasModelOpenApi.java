package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

/*
* Classe responsável por representar o resultado da consulta da lista de cozinhas por paginação
* */
@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;

    private Links _links;

    private PageModelOpenApi page;

    @ApiModel("cozinhasEmbeddedModel")
    @Data
    public class CozinhasEmbeddedModelOpenApi{

        private List<CozinhaModel> cozinhas;
    }
}
