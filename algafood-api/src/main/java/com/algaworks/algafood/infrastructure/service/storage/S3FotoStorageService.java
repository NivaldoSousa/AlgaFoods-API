package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3FotoStorageService implements FotoStorageService {

    /*
    * Api da amazon que será usada para fazer a implementação dos metodos abaixo
    * so que antes precisamos torna essa classe um componente Bean
    * pois ela não faz parte do componente spring
    * */
    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void armazenar(NovaFoto novaFoto) {

    }

    @Override
    public void remover(String nomeArquivo) {

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }
}
