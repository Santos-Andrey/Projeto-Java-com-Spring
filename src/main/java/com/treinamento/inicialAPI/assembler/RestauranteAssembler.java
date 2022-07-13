package com.treinamento.inicialAPI.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.model.CozinhaModel;
import com.treinamento.inicialAPI.model.RestauranteModel;

@Component
public class RestauranteAssembler {

	public RestauranteModel toModel(Restaurante restaurante) {
		CozinhaModel cozinhaModel = new CozinhaModel();
		RestauranteModel restauranteModel = new RestauranteModel();
		
		cozinhaModel.setId(restaurante.getCozinha().getId());
		cozinhaModel.setNome(restaurante.getCozinha().getNome());
		
		restauranteModel.setId(restaurante.getId());
		restauranteModel.setNome(restaurante.getNome());
		restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteModel.setCozinha(cozinhaModel);
		return restauranteModel;
	}
	
	public List <RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
	
}
