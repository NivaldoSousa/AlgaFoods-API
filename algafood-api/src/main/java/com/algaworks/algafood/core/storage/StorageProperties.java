package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/*
* Classe responsavel por representar as configurações feitas application.properties
* das chaves criadas do serviço do S3 da Amazon e armazenamento local
* */
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage") //aponta o prefixo das chaves do application.properties
public class StorageProperties {

    private Local local = new Local(); // representa algafood.storage.local
    private S3 s3 = new S3(); // representa algafood.storage.S3

    @Getter
    @Setter
    public class Local{
        private Path diretorioFotos; // Esse atributo representa essa configuração algafood.storage.local.diretorio-fotos
    }

    @Getter
    @Setter
    public class S3{
        private String idChaveAcesso; // Esse atributo representa algafood.storage.s3.id-chave-acesso

        private String chaveAcessoSecreta; // Esse atributo representa algafood.storage.s3.chave-acesso-secreta

        private String bucket; // Esse atributo representa algafood.storage.s3.bucket

        //Region da Amazon fornece uma enumeração com as regioes
        private Regions regiao; // Esse atributo representa algafood.storage.s3.regiao

        private String diretorioFotos; // Esse atributo representa algafood.storage.s3.diretorio-fotos
    }
}
