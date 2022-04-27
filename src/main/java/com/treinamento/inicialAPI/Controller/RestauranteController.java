package com.treinamento.inicialAPI.Controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;
import com.treinamento.inicialAPI.domain.service.CadastroRestauranteService;

public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService serviceRestaurante;
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long RestauranteId){
		Optional<Restaurante> restaurante = restauranteRepository.findById(RestauranteId);
		
		if(restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
		try {
			
			restaurante = serviceRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PutMapping("/{RestauranteId}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if(restaurante != null) {
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
			restauranteRepository.save(restauranteAtual.get());
			return ResponseEntity.ok(restauranteAtual.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/{RestauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
		Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);
		
		if(restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
		
	}

	//Implementação de mesclagem utilizando expressão lambda e um merge para mesclar as classes
	//Uso de um Field para armazenar e liberar acessos, um convert para converter dados para um ponto inicialmente dito
	//Reflection para juntar os valores
	//forEach como ciclo de repetição
	
	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			//System.out.println(nomePropriedade + "=" + valorPropriedade + "=" + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}
