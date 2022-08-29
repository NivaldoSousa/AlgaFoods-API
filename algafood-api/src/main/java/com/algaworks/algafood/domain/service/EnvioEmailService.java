package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

/*
* Interface responsavel pelos metodos de envio de e-mail
* */
public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {

        @Singular // essa anotação do lombok singulariza o set, fazendo com que ele se torne um atributo simples, com isso é possivel setar valores um a um
        private Set<String> destinatarios;

        @NonNull // obriga ter valor na hora da construção do objeto, caso nao tenha é lançada um exception
        private String assunto;

        @NonNull // obriga ter valor na hora da construção do objeto, caso nao tenha é lançada um exception
        private String corpo;
    }
}
