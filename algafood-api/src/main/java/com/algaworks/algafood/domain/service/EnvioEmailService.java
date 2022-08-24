package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

/*
* Interface responsavel pelos metodos de envio de e-mail
* */
public interface EnvioEmailService {

    void enviar(Messagem messagem);

    @Getter
    @Builder
    class Messagem{
        private Set<String> destinatarios;
        private String assunto;
        private String corpo;
    }
}
