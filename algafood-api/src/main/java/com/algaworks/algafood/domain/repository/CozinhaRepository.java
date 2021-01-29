package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    //List<Cozinha> consultarPorNome(String nome);

}

/*Um repository é uma camada onde podemos chamar os principais metodos com salvar,editar,deletar e buscar e entre outros
 nesse pacote repository nao temos a estrutura do codigo em si, somente as suas chamadas*/