package com.treinamento.inicialAPI.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.treinamento.inicialAPI.domain.model.Estado;

public class CidadeMixin {

	@JsonProperty("Título")
	private String nome;
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado Estado;
}
