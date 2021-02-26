package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) //Caso essa Exceção seja lançada ira retorna o erro http 404
public class EntidadeNaoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
