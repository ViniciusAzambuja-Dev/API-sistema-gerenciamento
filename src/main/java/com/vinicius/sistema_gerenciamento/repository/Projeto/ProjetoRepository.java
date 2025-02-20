package com.vinicius.sistema_gerenciamento.repository.Projeto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    @Query("SELECT obj FROM Projeto obj JOIN FETCH obj.usuario_responsavel WHERE obj.desativado = false")
    List<Projeto> findAllAtivado();
}