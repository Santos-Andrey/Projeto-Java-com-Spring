package com.treinamento.inicialAPI.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treinamento.inicialAPI.domain.model.Cidade;
import com.treinamento.inicialAPI.domain.model.repository.CidadeRepository;

@RestController
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	
	}
	
	@GetMapping
	public ResponseEntity<Cidade> buscar(@PathVariable Long Cidadeid){
		Optional<Cidade> cidade = cidadeRepository.findById(Cidadeid);
		
		if(cidade.isPresent()) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
