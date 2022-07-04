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

import com.treinamento.inicialAPI.domain.exception.NegocioException;
import com.treinamento.inicialAPI.domain.exception.RestauranteNaoEncontradoException;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;
import com.treinamento.inicialAPI.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService serviceRestaurante;
	
	
	@GetMapping("/listar")
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long RestauranteId){
		return serviceRestaurante.buscarOuFalhar(RestauranteId);
		
	}
	
	@PostMapping("/{adicionar}")
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante){
		
		try {
			return serviceRestaurante.salvar(restaurante);
		
		}catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PutMapping("/{RestauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante){
		
		Restaurante restauranteAtual = serviceRestaurante.buscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		
		try {
	     	return restauranteRepository.save(restauranteAtual);
	     		
		}catch(RestauranteNaoEncontradoException e) {
	     	throw new NegocioException(e.getMessage(), e);
	     		}
		}
	
	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId) {
		serviceRestaurante.Excluir(restauranteId);
	}
	
}
	

