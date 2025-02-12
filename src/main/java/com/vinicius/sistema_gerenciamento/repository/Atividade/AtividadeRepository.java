package com.vinicius.sistema_gerenciamento.repository.Atividade;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
    List<Atividade> findByProjetoId(int id);
}