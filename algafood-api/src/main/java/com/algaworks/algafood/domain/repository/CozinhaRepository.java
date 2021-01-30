package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    List<Cozinha> findBynome(String nome); // o findBy pode ser considerado como prefixo assim vc pode costomizar uma pesquisar

    //Containing significa que esse criterio de pesquisa tem o % no inicio e no final
    //desse jeito ele esta pesquisando a a propriedade nome desse jeito % nome %
    List<Cozinha> findTodasByNomeContaining(String nome);

}

/*Um repository é uma camada onde podemos chamar os principais metodos com salvar,editar,deletar e buscar e entre outros
 nesse pacote repository nao temos a estrutura do codigo em si, somente as suas chamadas*/