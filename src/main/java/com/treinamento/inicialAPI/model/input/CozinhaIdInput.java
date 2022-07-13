package com.treinamento.inicialAPI.model.input;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CozinhaIdInput {

	@NotNull
	private Long id;
	
}
