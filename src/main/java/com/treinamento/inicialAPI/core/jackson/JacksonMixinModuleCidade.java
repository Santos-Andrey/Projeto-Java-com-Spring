package com.treinamento.inicialAPI.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.treinamento.inicialAPI.domain.model.Cidade;
import com.treinamento.inicialAPI.model.mixin.CidadeMixin;

public class JacksonMixinModuleCidade extends SimpleModule {
	private static final long serialVersionUID = 1L;

	
	public  JacksonMixinModuleCidade() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}
	
}
