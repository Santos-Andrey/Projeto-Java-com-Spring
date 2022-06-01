package com.treinamento.inicialAPI.Controller.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
	
}
