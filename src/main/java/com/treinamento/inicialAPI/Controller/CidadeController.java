 package com.treinamento.inicialAPI.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.treinamento.inicialAPI.domain.exception.EstadoNaoEncontradoException;
import com.treinamento.inicialAPI.domain.exception.NegocioException;
import com.treinamento.inicialAPI.domain.model.Cidade;
import com.treinamento.inicialAPI.domain.model.repository.CidadeRepository;
import com.treinamento.inicialAPI.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping("/listar")
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	@GetMapping("/{buscarId}")
	public Cidade buscar(@PathVariable Long Cidadeid){
		return cadastroCidade.buscarOuFalhar(Cidadeid);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade cidade) {
		
		try {
		
			return cadastroCidade.salvar(cidade);
		
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody @Valid Cidade cidade) {
		Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		try {
		
			return cidadeRepository.save(cidadeAtual);
		
		}catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	} 
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirCidade(@PathVariable Long cidadeId) {
			cadastroCidade.excluir(cidadeId);
	}
	
}

