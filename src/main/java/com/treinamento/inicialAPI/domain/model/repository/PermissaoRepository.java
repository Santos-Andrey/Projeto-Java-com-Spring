package com.treinamento.inicialAPI.domain.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.treinamento.inicialAPI.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{
}
