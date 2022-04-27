package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Cozinha;
import com.treinamento.inicialAPI.domain.model.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void excluir(Long cozinhaId) {
		try {
		cozinhaRepository.deleteById(cozinhaId);
		
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Entidade %d não encontrada", cozinhaId));
		
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("A entidade %d esta em uso!",cozinhaId));
		
		}
		
	}
	
}
