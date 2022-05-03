package com.treinamento.inicialAPI.domain.service;

import java.util.List;

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
		List<Estado> estado = estadoRepository.findAll();
		
		if(estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Esta entidade %d não foi encontrada!", Estadoid));
		}
			cidade.setEstado((Estado) estado);
			return cidadeRespository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRespository.deleteById(cidadeId);
			
		}catch(EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format("Esta entidade %d está em uso!", cidadeId));
			
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(String.format("Esta entidade %d não foi encontrada!", cidadeId));
		}
	}
	
}
