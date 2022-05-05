package com.treinamento.inicialAPI.domain.model.repository;

import org.springframework.stereotype.Repository;

import com.treinamento.inicialAPI.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{


//	List<Cozinha> consultarPorNome(String nome);

	
}
