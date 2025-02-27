package com.vinicius.sistema_gerenciamento.repository.Atividade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
    @Query("SELECT obj FROM Atividade obj " +
        "WHERE obj.projeto.id = :id AND obj.desativado = false")
    List<Atividade> findByProjetoId(@Param("id") int projetoId);

    @Query("SELECT obj FROM Atividade obj " +
       "JOIN FETCH obj.usuario_responsavel " +
       "JOIN FETCH obj.projeto " +
       "WHERE obj.desativado = false")
    List<Atividade> findAllAtivado();

    @Query("SELECT obj FROM Atividade obj " +
        "JOIN UsuarioAtividade ua ON obj.id = ua.atividade.id " +
        "WHERE ua.usuario.id = :id")
    List<Atividade> findByUsuarioId(@Param("id") int usuarioId);
    
    @Query("SELECT COUNT(obj) > 0 FROM Atividade obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int atividadeId);

    @Modifying
    @Query("UPDATE Atividade obj SET obj.desativado = true WHERE obj.projeto.id = :id")
    void deleteByProjetoId(@Param("id") int projetoId);

    @Modifying
    @Query("UPDATE Atividade obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteAtividadeById(@Param("id") int atividadeId);
}