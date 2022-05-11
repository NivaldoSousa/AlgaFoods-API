package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //Diz que nao deve levar em conta esse repositorio, o spring ignora
public interface CustomJpaRepository<T,ID> extends JpaRepository<T, ID> {

	Optional<T> buscarPrimeiro();

	/*
	* Responsavel por remover a entidade do gerenciamento do banco de dados atraves do @Transactional
	* */
	void detach(T entity);
}
