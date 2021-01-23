package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listar();
    Cozinha buscar(Long id);
    Cozinha salvar(Cozinha cozinha);
    void remover(Long id);
}

/*Um repository é uma camada onde podemos chamar os principais metodos com salvar,editar,deletar e buscar e entre outros
 nesse pacote repository nao temos a estrutura do codigo em si, somente as suas chamadas*/