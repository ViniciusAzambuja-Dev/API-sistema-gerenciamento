package com.vinicius.sistema_gerenciamento.repository.Atividade;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.sistema_gerenciamento.model.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
}