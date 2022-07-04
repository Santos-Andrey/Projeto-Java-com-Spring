package com.treinamento.inicialAPI.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.model.mixin.RestauranteMixin;

@Component
public class JacksonMixinModule extends SimpleModule {
	private static final long serialVersionUID = 1L;

	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
	}
	
}
