package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    InputStream recuperar (String nomeArquivo);

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto){

        this.armazenar(novaFoto);

        if(StringUtils.isNotEmpty(nomeArquivoAntigo)){
            this.remover(nomeArquivoAntigo);
        }
    }

    /*
    * Gera um codigo unico para ser concatenado com o nome da foto
    * para evitar problemas de duplicação na hora de armazenar
    * */
    default String gerarNomeArquivo(String nomeOriginal){
        return UUID.randomUUID().toString() + "_" + nomeOriginal;
    }

    /*
    * Classe responsavel por receber os dados da foto para ser armazenada
    * */
    @Getter
    @Builder
    class NovaFoto{
        private String nomeArquivo;
        private InputStream inputStream; //fluxo de dados de entrada de um arquivo
    }
}
