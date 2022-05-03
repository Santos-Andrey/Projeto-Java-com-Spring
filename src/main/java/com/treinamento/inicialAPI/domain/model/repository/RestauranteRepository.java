package com.treinamento.inicialAPI.domain.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.treinamento.inicialAPI.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
			extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
			JpaSpecificationExecutor<Restaurante> {

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> ConsultarPorNome(String nome, @Param("id")Long cozinha);
	
	//List<Restaurante> findByNomeContainingAndcozinhaId(String nome, Long cozinha);
	
	
}
