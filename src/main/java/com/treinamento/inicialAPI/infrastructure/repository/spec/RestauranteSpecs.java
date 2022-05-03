package com.treinamento.inicialAPI.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.treinamento.inicialAPI.domain.model.Restaurante;

public class RestauranteSpecs {

	
	 public static Specification<Restaurante> comFreteGratis(){
		 return (root, query, Builder) -> 
		 Builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	 }
	 
	 public static Specification<Restaurante> comNomeSemelhante(String nome){
		 return (root, query, Builder) ->
		 Builder.equal(root.get("Nome"), "%" + nome + "%");
	 }
	 
}
