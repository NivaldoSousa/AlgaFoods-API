package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    //@RequestParam é o parametro que vai ser passado pelo usuario
    //"nome" é o parametro vai ser atribuido ao JPQL da query
   @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome){
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantePorNome(String nome, Long cozinhaId){
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantePorTaxaFrete(Double taxaInicial, Double taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantePornome(String nome){
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> restaurantePorTop2(String nome){
        return restauranteRepository.findTop2ByNomeContaining(nome);
    }
}
