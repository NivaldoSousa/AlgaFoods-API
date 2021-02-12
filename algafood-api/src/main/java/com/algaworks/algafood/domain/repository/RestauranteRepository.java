package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
//JpaSpecificationExecutor recebe uma specification para fazer as consultas utilizando o especifications criados no pacote spec

public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
		JpaSpecificationExecutor<Restaurante> {

	List<Restaurante> findByTaxaFreteBetween(Double taxaInicial, Double taxaFinal);

	// Consulata em JPQL utilizando uma query normal, como se fosse feita direto no
	// banco de dados
	// @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

	// Traz os 2 primeiros pois utiliza a palavra chave Top e em seguida voce diz
	// quantos quer trazer no caso 2
	List<Restaurante> findTop2ByNomeContaining(String nome);

}
