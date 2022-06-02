package com.treinamento.inicialAPI.Controller.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.PropertyAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> TratarEntidadeNaoEncontradoException(
			EntidadeNaoEncontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = Problema.builder()
				.status(status.value())
				.type("http://Apitreinamento.com.br/entidadenãoencontrada")
				.title("Esta Entidade não foi encontrada!")
				.details(ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(),
				status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> TratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		
			HttpStatus status = HttpStatus.CONFLICT;
			Problema problema = Problema.builder()
					.status(status.value())
					.type("http://Apitreinamento.com.br/EntidadeEmUso")
					.title("Entidade em Uso")
					.details(ex.getMessage())
					.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(),
				status, request);	
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> TratarNegocioException(NegocioException ex, WebRequest request){
		
			HttpStatus status = HttpStatus.BAD_REQUEST;
			Problema problema = Problema.builder()
					.status(status.value())
					.type("http://Apitreinamento.com.br/NegocioException")
					.title("NegocioException")
					.details(ex.getMessage())
					.build();
		
			return handleExceptionInternal(ex, problema, new HttpHeaders(),
					status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
			
		if(body == null) {
				body = Problema.builder()
						.status(status.value())
						.title(status.getReasonPhrase())
						.build();
		}else if( body instanceof String){
			body = Problema.builder()
					.status(status.value())
					.title((String)body)
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
	
			//Desafio de Implementação da aula
		}else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((InvalidFormatException) rootCause, headers, status, request);
		}
		
		Problema problema = Problema.builder()
				.type("http://Apitreinamento.com.br/Mensagem Incompreensivel")
				.title("Mensagem Incompreensivel!")
				.details("Consulta feita de forma incorreta, verifique error de sintaxe.")
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(),
				status, request);
	}
	
	
	//Desafio de Implementação da aula
	private ResponseEntity<Object> handlePropertyBinding(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
			
			String path = ex.getPath().stream()
					.map(ref -> ref.getFieldName())
					.collect(Collectors.joining("."));
			
			String details = String.format("A propriedade '%s' é inválida"
					+ "corrija ou remova e tente novamente", path);
			
			Problema problema = Problema.builder()
					.type("\"http://Apitreinamento.com.br/Propriedade-Invalida")
					.title("Propriedade Invalida")
					.details(details)
					.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		//BUSCAR NOMES DE ONDE A EXCEPTION RETORNOU
		//ex.getPath().forEach(ref -> System.out.println(ref.getFieldName()));
		
		//Mapeamento com Stream e Map para concatenar classes em uma só
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
				
		//Formatação e junção do mapeamento com a String que aparecera na exception da requisição
		String details = String.format("A propriedade '%s' recebeu o valor '%s'"
				+ "que é de um tipo inválido. Corrija e informe o valor compatível com o tipo '%s'",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		//Builder do problema com as atribuições customizadas
		Problema problema = Problema.builder()
				.type("http://Apitreinamento.com.br/Mensagem Incompreensivel")
				.title("Mensagem Incompreensivel!")
				.details(details)
				.build();

		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	
	@SuppressWarnings("unused")
	private ResponseEntity<Object> TratarParametroInvalido(PropertyAccessException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request){
		
		String details = String.format("O parâmetro de URL '%s' recebeu valor '%s' que é de um tipo"
				+ "invalido! Corrija e informe um valor compativel",
				ex.getPropertyName(), ex.getValue());
		
		Problema problema = Problema.builder()
				.type("http://Apitreinamento.com.br/Parametro-Invalido")
				.title("Parametro Invalido")
				.details(details)
				.build();
	
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

}

