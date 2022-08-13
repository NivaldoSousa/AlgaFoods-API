package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Classe responsavel por amarzenar arquivos localmente
 * */
@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Value("${algafood.storage.local.diretorio-fotos}")// recuperar o valor do caminho configurado no application.properties
    private Path diretorioFotos;

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            //caminho da pasta onde será salvo
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            //apartir do inputStream sera copiado para o caminho do path
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
          throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    /*
    * Devolve o path completo, com o caminho mais o nome do arquivo
    * */
    private Path getArquivoPath(String nomeArquivo) {
        //Junta o caminho mais o nome do arquivo
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }
}
