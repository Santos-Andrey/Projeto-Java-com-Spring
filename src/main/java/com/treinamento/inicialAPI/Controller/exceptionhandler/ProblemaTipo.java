package com.treinamento.inicialAPI.Controller.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemaTipo {
	
	ERRO_DE_SISTEMA("/Error-inesperado-no-sistema", "Error Interno no Sistema"),
	PARAMETRO_INVALIDO("/Parametro-Invalido", "Parametro Invalido!");
	
	private String title;
	private String URI;
	
	ProblemaTipo(String title, String path) {
		this.URI = "https://APItreinamento.com.br" + path;
		this.title = title;
	}
	
}
