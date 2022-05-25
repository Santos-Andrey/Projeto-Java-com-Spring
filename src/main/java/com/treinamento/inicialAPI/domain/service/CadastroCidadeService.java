package com.treinamento.inicialAPI.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Cidade;
import com.treinamento.inicialAPI.domain.model.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String ENTIDADE_EM_USO_MSG = "Esta entidade %d está em uso!";

	private static final String ENTIDADE_NÃO_ENCONTRADA_MSG = "Esta entidade %d não foi encontrada!";

	@Autowired
	private CidadeRepository cidadeRespository;
	
	public Cidade salvar(Cidade cidade) {
		return cidadeRespository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRespository.deleteById(cidadeId);
			
		}catch(EntidadeEmUsoException e) {
			throw new EntidadeEmUsoException(String.format(ENTIDADE_EM_USO_MSG, cidadeId));
			
		}catch(EntidadeNaoEncontradaException e) {
			throw new EntidadeNaoEncontradaException(String.format(ENTIDADE_NÃO_ENCONTRADA_MSG, cidadeId));
		}
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRespository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(ENTIDADE_NÃO_ENCONTRADA_MSG));
	}
	
}
