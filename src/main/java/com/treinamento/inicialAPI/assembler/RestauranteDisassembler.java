package com.treinamento.inicialAPI.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.model.input.RestauranteInput;

@Component
public class RestauranteDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
}
