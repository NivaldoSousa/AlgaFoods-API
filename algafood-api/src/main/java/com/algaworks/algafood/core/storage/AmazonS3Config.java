package com.algaworks.algafood.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Classe de configuração que permirti devolver uma instancia do AmazonS3
 */

@Configuration
public class AmazonS3Config {

    @Autowired
    private StorageProperties storageProperties;

    /*
    * retorna a instancia do AmazonS3
    * nesse momento o amazonS3 se torna um componente Spring
    * */
    @Bean
    public AmazonS3 amazonS3(){
        //BasicAWSCredentials criar uma variavel com a credencias configuradas, chave de acesso e chave secreta
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(), storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)) //passando as credencias para a instancia
                .withRegion(storageProperties.getS3().getRegiao()) // passando a regiao para a instancia
                .build(); // criar uma instancia
    }
}
