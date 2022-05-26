package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.CozinhaNaoEncontradaException;
import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String COZINHA_EM_USO_MSG = "A entidade %d esta em uso!";
	
	@Autowired CozinhaRepository cozinhaRepository;

	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	
	public void excluir(Long cozinhaId) {
		try {
		cozinhaRepository.deleteById(cozinhaId);
		
		}catch(EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException((cozinhaId));
		
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(COZINHA_EM_USO_MSG,cozinhaId));
		}
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
	
}
