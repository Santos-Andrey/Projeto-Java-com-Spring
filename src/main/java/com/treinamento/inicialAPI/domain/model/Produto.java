package com.treinamento.inicialAPI.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Entity
public class Produto {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(name = "descrição_do_produto")
	private String descricao;
	
	@Column(name = "preço",nullable = false)
	private BigDecimal preco;
	
	@Column(nullable = false)
	private boolean ativo;
	
	@JsonIgnore
	@ManyToOne
	@JoinTable(name = "restaurante")
	private Restaurante restaurante;
	
}
