package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 * Interface responsável por manter as anotações de documentação do swagger de todos os metodos da classe CozinhaController
 *
 * */
@Api(tags = "Cozinhas") // É uma anotação que registra que esse controller é um recurso do swagger
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas com paginação")
    public Page<CozinhaModel> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID") //Muda a assinatura do metodo no swagger
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class), // Customiza os codigos de retorno da API de forma individual
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) // Customiza os codigos de retorno da API de forma individual
    })
    public CozinhaModel buscar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);

    @ApiOperation("Cadastra uma cozinha") //Muda a assinatura do metodo no swagger
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"), // Customiza os codigos de retorno da API de forma individual
    })
    public CozinhaModel adicionar(
            @ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true)
            CozinhaInput cozinhaInput);

    @ApiOperation("Atualiza uma cozinha por ID") //Muda a assinatura do metodo no swagger
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"), // Customiza os codigos de retorno da API de forma individual
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) // Customiza os codigos de retorno da API de forma individual
    })
    public CozinhaModel atualizar(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId,

            @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true)
            CozinhaInput cozinhaInput);

    @ApiOperation("Exclui uma cozinha por ID") //Muda a assinatura do metodo no swagger
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"), // Customiza os codigos de retorno da API de forma individual
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class) // Customiza os codigos de retorno da API de forma individual
    })
    public void remover(
            @ApiParam(value = "ID de uma cozinha", example = "1", required = true)
            Long cozinhaId);
}
