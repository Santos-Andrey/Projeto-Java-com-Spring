//Padronizando resposta para exceptions -> utilizando Padrão Problem Details for HTTP APIs -> RFC-7807

package com.treinamento.inicialAPI.Controller.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {
	
	private Integer status;
	private String type;
	private String title;
	private String details;
	
	}
