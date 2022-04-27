package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.CozinhaRepository;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()-> new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
}
