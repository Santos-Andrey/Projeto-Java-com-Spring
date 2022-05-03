package com.treinamento.inicialAPI.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.treinamento.inicialAPI.domain.model.Restaurante;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepository;
import com.treinamento.inicialAPI.domain.model.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{

	
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
			
		//Utilizando CRITERIA-API
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);
		
		var Predicates = new ArrayList<Predicate>();
		
			if(StringUtils .hasText(nome)) {
			Predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
			}

			if(taxaFreteInicial != null) {
			Predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
			}
			
			if(taxaFreteFinal != null) {
			Predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
			}
		
		
			criteria.where(Predicates.toArray(new Predicate[0]));
		
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
					return query.getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis()
				.add(comNomeSemelhante(nome)));
	
	}
}

//ex emplo de consulta(querys) dinamica com jpql
		/*
		var jpql = new StringBuilder();
				jpql.append("from Restaurante where 0 = 0 ");
		
				var parametros = new HashMap<String, Object>();
				
				if(StringUtils.hasLength(nome)) {
					jpql.append("where nome like :nome ");
					parametros.put("nome", "%" + nome + "%");
				}
				
				if(taxaFreteInicial != null) {
					jpql.append("and TaxaFrete <= :taxaInicial  ");
					parametros.put("taxaInicial", taxaFreteInicial);
				}
				
				if(taxaFreteFinal != null) {
					jpql.append("and taxaFrete <= :taxaFinal ");
					parametros.put("taxaFinal", taxaFreteFinal);
				}
				
		TypedQuery <Restaurante> query = manager.createQuery
				(jpql.toString(), Restaurante.class);
				
				parametros.forEach((chave, valor) -> query.setParameter(chave,valor));
				return query.getResultList();
	*/