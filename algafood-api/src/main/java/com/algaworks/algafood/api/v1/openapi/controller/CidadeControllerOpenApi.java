package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

/*
 * Interface responsável por manter as anotações de documentação do swagger de todos os metodos da classe CidadeController
 *
 * */
@Api(tags = "Cidades") // É uma anotação que registra que esse controller é um recurso do swagger
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
        //Muda a assinatura do metodo no swagger
    CollectionModel<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID") //Muda a assinatura do metodo no swagger
    @ApiResponses({@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class), // Customiza os codigos de retorno da API de forma individual
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

    @ApiOperation("Cadastra uma cidade") //Muda a assinatura do metodo no swagger
    @ApiResponses({@ApiResponse(code = 201, message = "Cidade cadastrada")})
// Customiza os codigos de retorno da API de forma individual
    CidadeModel adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

    @ApiOperation("Atualiza uma cidade por ID") //Muda a assinatura do metodo no swagger
    @ApiResponses({@ApiResponse(code = 200, message = "Cidade atualizada"), // Customiza os codigos de retorno da API de forma individual
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    CidadeModel atualizar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
                          @ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados", required = true) CidadeInput cidadeInput);

    @ApiOperation("Exclui uma cidade por ID") //Muda a assinatura do metodo no swagger
    @ApiResponses({@ApiResponse(code = 204, message = "Cidade excluída"), // Customiza os codigos de retorno da API de forma individual
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    void remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);
}
