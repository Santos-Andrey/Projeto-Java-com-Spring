package com.treinamento.inicialAPI.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public class Usuario {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JoinColumn
	private String nome;
	
	@JoinColumn
	private String email;
	
	@JoinColumn
	private String senha;
	
	
	@CreationTimestamp
	@JoinColumn(nullable = false, columnDefinition = "DateTime")
	private LocalDateTime dataCadastro;

	
	@ManyToMany
	@JoinTable(name = "Grupos_usuario")
	private List<Grupos> grupos = new ArrayList<>();
	
}
