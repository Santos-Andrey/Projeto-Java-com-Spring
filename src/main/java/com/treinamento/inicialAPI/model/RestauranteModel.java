package com.treinamento.inicialAPI.model;

import java.math.BigDecimal;

import lombok.Data;
@Data

public class RestauranteModel {

	private Long id;
	private String nome;
	private BigDecimal TaxaFrete;
	
	private CozinhaModel cozinha;
	
	
}
