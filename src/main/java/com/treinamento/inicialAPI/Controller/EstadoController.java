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

import com.treinamento.inicialAPI.domain.model.Estado;
import com.treinamento.inicialAPI.domain.model.repository.EstadoRepository;
import com.treinamento.inicialAPI.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	
	@GetMapping("/{Listar}")
	public List <Estado> listarE(){
		return estadoRepository.findAll();
	}

	@GetMapping("/{EstadoId}")
	public Estado buscar(@PathVariable Long EstadoId) {
		return cadastroEstado.buscarOuFalhar(EstadoId);
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid Estado estado) {
		return cadastroEstado.salvar(estado);
	}

	@PutMapping("/{EstadoId}")
	public Estado atualizar(@PathVariable Long EstadoId, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(EstadoId);
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		return estadoRepository.save(estadoAtual);
		
	}
	@DeleteMapping("/{EstadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirEstado(@PathVariable Long EstadoId) {
		cadastroEstado.Excluir(EstadoId);
	}
}


