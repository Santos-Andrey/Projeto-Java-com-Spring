package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Estado;
import com.treinamento.inicialAPI.domain.model.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void Excluir(Long Estadoid) {
		try {
			estadoRepository.deleteById(Estadoid);
			
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(String.format("Entidade %d não encontrada", Estadoid));
			
		}catch(EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format("Entidade %d em uso!", Estadoid));
		}
	}
	
}
