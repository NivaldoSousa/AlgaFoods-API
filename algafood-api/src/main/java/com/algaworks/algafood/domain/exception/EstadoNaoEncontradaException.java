package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) //Caso essa Exceção seja lançada ira retorna o erro http 404
public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
