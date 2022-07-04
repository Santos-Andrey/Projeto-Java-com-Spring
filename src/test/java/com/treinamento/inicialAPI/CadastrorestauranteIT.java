package com.treinamento.inicialAPI;

import static io.restassured.RestAssured.given;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastrorestauranteIT {
	
	@LocalServerPort
	private int port; 
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Before
	public void SetUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
	}
	
	@Test
	public void deveResultarOKaoConsultar() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveFalharAoExcluirRestauranteEmUso() {
		restauranteRepository.deleteById(1L);
	}
	
	@Test
	public void deveResultarOKaoAdicionar() {
		given()
			.body("{ \"nome\": \"Italiano\" }")
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(201);
	}
	
	public void deveFalharAoExcluirRestauranteInexistente() {
		restauranteRepository.deleteById(100L);
	}
	
}
