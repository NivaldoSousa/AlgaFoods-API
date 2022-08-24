package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Classe de configuração que permirti devolver uma instancia do AmazonS3
 */

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

    /*
    * retorna a instancia do AmazonS3
    * nesse momento o amazonS3 se torna um componente Spring
    * */
    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3") // essa anotação faz com que o bean seja criado apenas se uma condição for satisfeita.
    public AmazonS3 amazonS3(){
        //BasicAWSCredentials criar uma variavel com a credencias configuradas, chave de acesso e chave secreta
        var credentials = new BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(), storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)) //passando as credencias para a instancia
                .withRegion(storageProperties.getS3().getRegiao()) // passando a regiao para a instancia
                .build(); // criar uma instancia
    }

    /*
     * Define qual service de armazenamento será instanciado local ou S3
     * */
    @Bean
    public FotoStorageService fotoStorageService() {
        if (StorageProperties.TipoStorage.S3.equals(storageProperties.getTipo())) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService();
        }
    }
}
