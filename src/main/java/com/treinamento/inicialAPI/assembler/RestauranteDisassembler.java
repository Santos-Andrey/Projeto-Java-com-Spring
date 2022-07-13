package com.treinamento.inicialAPI.assembler;

import org.springframework.stereotype.Component;

import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.model.input.RestauranteInput;

@Component
public class RestauranteDisassembler {

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		Restaurante restaurante = new Restaurante();
		Cozinha cozinha = new Cozinha();
		
		cozinha.setId(restauranteInput.getCozinhaId().getId());
		restaurante.setNome(restauranteInput.getNome());
		restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
}
