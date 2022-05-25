package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Estado;
import com.treinamento.inicialAPI.domain.model.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String ENTIDADE_EM_USO_MSG = "Entidade %d em uso!";
	private static final String ENTIDADE_NÃO_ENCONTRADA_MSG = "Entidade %d não encontrada";
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void Excluir(Long Estadoid) {
		try {
			estadoRepository.deleteById(Estadoid);
			
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(String.format(ENTIDADE_NÃO_ENCONTRADA_MSG, Estadoid));
			
		}catch(EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO_MSG, Estadoid));
		}
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException(ENTIDADE_NÃO_ENCONTRADA_MSG));
	}
	
}
