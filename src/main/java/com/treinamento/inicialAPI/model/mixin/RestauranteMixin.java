package com.treinamento.inicialAPI.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.Endereco;
import com.treinamento.inicialAPI.domain.model.FormaPagamento;
import com.treinamento.inicialAPI.domain.model.Produto;

public class RestauranteMixin {

	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)					
	private Cozinha cozinha;
	
	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	private Endereco endereco;
	
	
	@JsonIgnore
	private List<Produto> produto = new ArrayList<>();
	
	
}
