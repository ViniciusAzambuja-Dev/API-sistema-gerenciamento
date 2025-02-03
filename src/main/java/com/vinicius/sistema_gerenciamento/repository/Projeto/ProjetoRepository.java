package com.vinicius.sistema_gerenciamento.repository.Projeto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.sistema_gerenciamento.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {
}