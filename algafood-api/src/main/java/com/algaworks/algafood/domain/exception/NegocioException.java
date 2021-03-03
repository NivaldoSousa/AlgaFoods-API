package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NegocioException(String mensagem) {
        super(mensagem);
    }
    
    // Throwable e classe pai de todas as exceçoes, com ele a causa do erro sera mostrado no console
    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
