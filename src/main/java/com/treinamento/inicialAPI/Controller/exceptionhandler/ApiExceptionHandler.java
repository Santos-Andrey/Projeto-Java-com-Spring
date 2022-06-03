package com.treinamento.inicialAPI.Controller.exceptionhandler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.treinamento.inicialAPI.domain.exception.EntidadeEmUsoException;
import com.treinamento.inicialAPI.domain.exception.EntidadeNaoEncontradaException;
import com.treinamento.inicialAPI.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private static final String MSG_ERROR_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema."
			+ "Tente novamente e se o problema persistir, entre em contato"
			+ "com o administrador do sistema.";

	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request){
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemaTipo problemaTipo = ProblemaTipo.ERRO_DE_SISTEMA;
		System.out.println(problemaTipo);
		
		String details = MSG_ERROR_GENERICA_USUARIO_FINAL;
		
		ex.printStackTrace();
		
		Problema problema = Problema.builder()
				.details(details)
				.title("Erro no Sistema")
				.status(status.value())
				.type("http://Apitreinamento.com.br/error-no-sistema")
				.build();
	
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> TratarEntidadeNaoEncontradoException(
			EntidadeNaoEncontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		Problema problema = Problema.builder()
				.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
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
					.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
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
					.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
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
						.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
						.status(status.value())
						.title(status.getReasonPhrase())
						.build();
				
		}else if( body instanceof String){
			body = Problema.builder()
					.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
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
				.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
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
					
					//Mensagem caso o front da API queira passar para o usuário, uma mensagem legivel ao usuario
					.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
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
				.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
				.type("http://Apitreinamento.com.br/Mensagem Incompreensivel")
				.title("Mensagem Incompreensivel!")
				.details(details)
				.build();

		return handleExceptionInternal(ex, problema, headers, status, request);
		}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request){
		
		if(ex instanceof MethodArgumentTypeMismatchException ) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException)ex, headers, status, request);
		}
	
			return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException
			 ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		ProblemaTipo problemaTipo = ProblemaTipo.PARAMETRO_INVALIDO;
		System.out.println(problemaTipo);
		
		String details = String.format("O parametro de URL '%s' recebeu valor '%s'"
				+ "que é invalido. Corrija e informe um valor do tipo '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		Problema problema = Problema.builder()
				.userMessage(MSG_ERROR_GENERICA_USUARIO_FINAL)
				.type("http://Apitreinamento.com.br/parametro-Invalido")
				.title("parametro-Invalido!")
				.details(details)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request) ;
		
	}
}
		




