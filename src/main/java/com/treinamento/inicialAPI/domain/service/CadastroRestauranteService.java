package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String ENTIDADE_EM_USO = "Entidade em uso %d";
	private static final String ENTIDADE_NÃO_ENCONTRADA_MSG = "Não existe cadastro de cozinha com código %d";

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
		
		}catch (EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(ENTIDADE_NÃO_ENCONTRADA_MSG);
			
		}catch (EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO, restauranteId));
		}
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(ENTIDADE_NÃO_ENCONTRADA_MSG));
		
	}
	
	
}
