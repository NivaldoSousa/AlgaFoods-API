package com.algaworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
