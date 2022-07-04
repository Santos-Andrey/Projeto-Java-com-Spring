package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.exception.EstadoNaoEncontradoException;
import com.treinamento.inicialAPI.domain.model.Estado;
import com.treinamento.inicialAPI.domain.model.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String ENTIDADE_EM_USO_MSG = "Entidade %d em uso!";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	@Transactional
	public void Excluir(Long estadoid) {
		try {
			estadoRepository.deleteById(estadoid);
			
		}catch(EntidadeNaoEncontradaException e) {
			throw new EstadoNaoEncontradoException(estadoid);
			
		}catch(EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO_MSG, estadoid));
		}
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
	
}
