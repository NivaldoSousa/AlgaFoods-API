package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;


    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
        //Como o findById retorna um Optional o metodo orElseThrow ele busca o conteudo do Optional e verifica se tem
        //conteudo, caso nao tenha ele lança o Execption que foi criado, caso tenha ele retorna um objeto no caso (cozinha).
        restaurante.setCozinha(cozinha);
        return restauranteRepository.salvar(restaurante);
    }


    /*Uma forma de se fazer utilizando o Optional
    *public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);

        if (cozinha.isEmpty()) { //verifica se esta vazio
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
        }
        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.salvar(restaurante);
    }*/
}