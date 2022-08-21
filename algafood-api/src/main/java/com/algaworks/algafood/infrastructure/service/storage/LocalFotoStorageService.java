package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Classe responsavel por amarzenar arquivos localmente
 * */
//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties; // Injetando a classe para obter as configurações do applicationProperties

    @Override
    public void armazenar(NovaFoto novaFoto) {

        try {
            //caminho da pasta + nome arquivo onde será salvo
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            //apartir do inputStream sera copiado para o caminho do path
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception e) {
          throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {

        try {
            //caminho da pasta + nome arquivo onde será removido
            Path arquivoPath = getArquivoPath(nomeArquivo);

            //remove o arquivo apartir do path contendo o caminho mais o nome da foto
            Files.deleteIfExists(arquivoPath);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir o arquivo", e);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {

        try {
            //caminho da pasta + nome arquivo onde será removido
            Path arquivoPath = getArquivoPath(nomeArquivo);

            FotoRecuperada fotoRecuperada = FotoRecuperada.builder().inputStream(Files.newInputStream(arquivoPath)).build();

            //criar um novo inputStream buscando o arquivo apartir do path
            return fotoRecuperada;
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    /*
    * Devolve o path completo, com o caminho mais o nome do arquivo
    * */
    private Path getArquivoPath(String nomeArquivo) {
        //Junta o caminho mais o nome do arquivo
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
