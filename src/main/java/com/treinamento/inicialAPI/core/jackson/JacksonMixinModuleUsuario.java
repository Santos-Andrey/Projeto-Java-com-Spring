package com.treinamento.inicialAPI.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.treinamento.inicialAPI.domain.model.Usuario;
import com.treinamento.inicialAPI.model.mixin.UsuarioMixin;

public class JacksonMixinModuleUsuario  extends SimpleModule{
	private static final long serialVersionUID = 1L;

	public JacksonMixinModuleUsuario() {
		setMixInAnnotation(Usuario.class, UsuarioMixin.class);
	}
	
}
