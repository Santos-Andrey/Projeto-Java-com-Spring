package com.treinamento.inicialAPI.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.treinamento.inicialAPI.domain.model.Cozinha;

import lombok.Data;
import lombok.NonNull;

@JsonRootName("Cozinhas")
@Data
public class CozinhasXMLWrapper {
	
	@NonNull
	private List<Cozinha> cozinhas;
	
}
