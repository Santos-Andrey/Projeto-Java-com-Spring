package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.RestauranteNaoEncontradoException;
import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String ENTIDADE_EM_USO = "Entidade em uso %d";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	CadastroRestauranteService cadastroRestaurante;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId); 
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
	
	public void Excluir(Long restauranteId){
		try {
			restauranteRepository.deleteById(restauranteId);
		
		}catch (RestauranteNaoEncontradoException e) {
			throw new RestauranteNaoEncontradoException(restauranteId);
			
		}catch (EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
		
	}
	
	
}
