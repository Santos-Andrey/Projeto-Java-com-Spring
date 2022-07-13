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

import com.treinamento.inicialAPI.assembler.RestauranteAssembler;
import com.treinamento.inicialAPI.assembler.RestauranteDisassembler;
import com.treinamento.inicialAPI.domain.exception.NegocioException;
import com.treinamento.inicialAPI.domain.exception.RestauranteNaoEncontradoException;
import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;
import com.treinamento.inicialAPI.domain.service.CadastroRestauranteService;
import com.treinamento.inicialAPI.model.RestauranteModel;
import com.treinamento.inicialAPI.model.input.RestauranteInput;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	
	@Autowired
	private RestauranteRepository restauranteRepository;
	@Autowired
	private CadastroRestauranteService serviceRestaurante;
	@Autowired
	private RestauranteAssembler restauranteAssembler;
	@Autowired
	private RestauranteDisassembler restauranteDisassembler;
	
	
	
	@GetMapping("/listar")
	public List<RestauranteModel> listar(){
		return restauranteAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	
	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long RestauranteId){
			Restaurante restaurante =  serviceRestaurante.buscarOuFalhar(RestauranteId);
		return restauranteAssembler.toModel(restaurante);
	}

	
	@PostMapping("/{adicionar}")
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput){
		
		try {
			Restaurante restaurante = restauranteDisassembler.toDomainObject(restauranteInput);
			return restauranteAssembler.toModel(serviceRestaurante.salvar(restaurante));
		
		}catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	
	@PutMapping("/{RestauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput){
		
		Restaurante restaurante = restauranteDisassembler.toDomainObject(restauranteInput);
		
		Restaurante restauranteAtual = serviceRestaurante.buscarOuFalhar(restauranteId);
		
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
		
			try {
		     	return restauranteAssembler.toModel(restauranteRepository.save(restauranteAtual));
		     		
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
	

