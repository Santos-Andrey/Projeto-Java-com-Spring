package com.treinamento.inicialAPI.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.treinamento.inicialAPI.domain.model.Permissao;

public class GruposMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private List<Permissao> permissoes = new ArrayList<>();
	
}
