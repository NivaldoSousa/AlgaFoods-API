package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, Double taxaFreteInicial, Double taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
