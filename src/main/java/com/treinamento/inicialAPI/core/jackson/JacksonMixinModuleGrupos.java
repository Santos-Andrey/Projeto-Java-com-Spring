package com.treinamento.inicialAPI.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.treinamento.inicialAPI.domain.model.Grupos;
import com.treinamento.inicialAPI.model.mixin.GruposMixin;

public class JacksonMixinModuleGrupos extends SimpleModule{
	private static final long serialVersionUID = 1L;

	public JacksonMixinModuleGrupos() {
		setMixInAnnotation(Grupos.class, GruposMixin.class);
	}
	
}
