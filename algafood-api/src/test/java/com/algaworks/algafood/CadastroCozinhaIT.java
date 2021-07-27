package com.algaworks.algafood;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//serve para da suporte as testes, com ele é possivel se beneficiar dos recursos do spring como injeçao de dependencias e outros
@RunWith(SpringRunner.class)
//Funcionalidades do Spring para os testes
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // --> Random Port serve para levantar um servidor de porta aleatoria web para fazer os teste
@TestPropertySource	("/application-test.properties") // Utilizando o aplication properties de test do banco da dados de teste																
public class CadastroCozinhaIT {

	// Essa anotaçao na variavel serve pra ser injetada no numero da porta que vem
	// do Random Port
	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner datadabeCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	// Metodo para transforma em constantes o basePath e port, para simplificar o
	// codigo de teste
	@Before
	public void steUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Metodo quando falha o teste, apresenta um log
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		datadabeCleaner.clearTables();
		prepararDados();

	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		RestAssured.given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveConter2Cozinhas_QuandoConsultarCozinhas() {
		RestAssured.given().accept(ContentType.JSON).when().get().then().body("", hasSize(2));

	}
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		RestAssured.given().body("{\"nome\": \"Chinesa\" }").contentType(ContentType.JSON).accept(ContentType.JSON)
				.when().post().then().statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaStatusCorretos_QuandoConsultarCozinhaExistente() {
		RestAssured.given().pathParam("cozinhaId", 2).accept(ContentType.JSON).when().get("/{cozinhaId}").then()
				.statusCode(HttpStatus.OK.value()).body("nome", equalTo("Americana"));
	}
	
	@Test
	public void deveRetornarRespostaStatus404_QuandoConsultarCozinhaInexistente() {
		RestAssured.given().pathParam("cozinhaId", 100).accept(ContentType.JSON).when().get("/{cozinhaId}").then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Americana");
		cozinhaRepository.save(cozinha2);
	}
}
