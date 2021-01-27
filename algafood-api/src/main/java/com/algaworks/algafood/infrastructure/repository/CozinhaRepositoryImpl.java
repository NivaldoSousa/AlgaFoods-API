package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    private EntityManager manager;

    //Metodo JPQL para consulta
    @Override
    public List<Cozinha> consultarPorNome(String nome){
        return manager.createQuery("from Cozinha where nome like :nome", Cozinha.class)
                .setParameter("nome","%" + nome + "%").getResultList();
    }


    @Override
    public List<Cozinha> listar() {
        return manager.createQuery("from Cozinha", Cozinha.class)
                .getResultList();
    }

       /* Um exemplo de como pode tambem ser feito
        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
           return  query.getResultList();*/


    @Override
    public Cozinha buscar(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    @Override
    public Cozinha salvar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Transactional
    @Override
    public void remover(Long id) {
      Cozinha cozinha = buscar(id);

      if(cozinha == null){
          //retorno de exeçao quando o objeto esta vazio, (1) ==> é paramentro siginifica que para remover era esperado no minimo 1 objeto
          throw new EmptyResultDataAccessException(1);
      }
      manager.remove(cozinha);
    }
}
