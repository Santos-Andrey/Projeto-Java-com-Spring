package com.treinamento.inicialAPI.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.treinamento.inicialAPI.domain.model.Grupos;

public class UsuarioMixin {

	@JsonIgnore
	private OffsetDateTime dataCadastro;

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private List<Grupos> grupos = new ArrayList<>();
	
}
