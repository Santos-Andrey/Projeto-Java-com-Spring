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

import com.treinamento.inicialAPI.domain.model.Estado;
import com.treinamento.inicialAPI.domain.model.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public List <Estado> listarE(){
		return estadoRepository.findAll();
	}

	@GetMapping("/{EstadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long EstadoId) {
		Optional<Estado> estado = estadoRepository.findById(EstadoId);
	
	
		if(estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
}

