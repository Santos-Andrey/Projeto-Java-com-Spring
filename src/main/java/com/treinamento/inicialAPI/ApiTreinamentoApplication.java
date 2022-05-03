package com.treinamento.inicialAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.treinamento.inicialAPI.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class ApiTreinamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTreinamentoApplication.class, args);
	}

}
