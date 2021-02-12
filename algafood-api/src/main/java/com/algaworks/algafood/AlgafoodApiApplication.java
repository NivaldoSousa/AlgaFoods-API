package com.algaworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}
}
/*
 * @SpringBootApplication significa que todos os pacotes que possuem anotaçoes
 * quaisquer do Spring se torna legivel pois essa
 * anotaçao @SpringBootApplication pois uma anotaçao @ComponentScan onde isso é
 * possivel.
 */
