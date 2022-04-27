package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Cidade;
import com.treinamento.inicialAPI.domain.model.Estado;
import com.treinamento.inicialAPI.domain.model.repository.CidadeRepository;
import com.treinamento.inicialAPI.domain.model.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRespository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		Long Estadoid = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(Estadoid);
		
		if(estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Esta entidade %d não foi encontrada!", Estadoid));
		}
			cidade.setEstado(estado);
			return cidadeRespository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			return cidadeRespository.deleteById(cidadeId);
			
		}catch(EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format("Esta entidade %d está em uso!", cidadeId));
			
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(String.format("Esta entidade %d não foi encontrada!", cidadeId));
		}
	}
	
}
