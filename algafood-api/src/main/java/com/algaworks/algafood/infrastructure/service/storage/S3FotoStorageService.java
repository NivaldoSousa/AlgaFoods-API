package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
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

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            // o PutObjectRequest representar o pay-load da requisição que sera feita para a api da amazon para colocar um objeto
            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(), //definindo o nome do bucket
                    caminhoArquivo, // caminho da pasta mais nome da foto
                    novaFoto.getInputStream(), //inputStream da foto
                    objectMetadata // tipo de arquivo ex: jpeg, png etc
            ). withCannedAcl(CannedAccessControlList.PublicRead); // Habilitando o acesso de leitura do arquivo publicamente

            //Chamada para API da amazon S3
            amazonS3.putObject(putObjectRequest);

        } catch (Exception e) {
            throw new StorageException("Não foi possivel enviar arquivo para Amazon S3", e);
        }
    }

    /*
    * retorna a String formatada com o nome do caminho da pasta criada na amazon com o nome da foto
    * */
    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

    @Override
    public void remover(String nomeArquivo) {

    }

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }
}
