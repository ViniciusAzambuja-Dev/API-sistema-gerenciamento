package com.vinicius.sistema_gerenciamento.repository.Horas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;

public interface HorasRepository extends JpaRepository<LancamentoHora, Integer>{  
    @Query("SELECT obj FROM LancamentoHora obj " +
        "WHERE obj.atividade.id = :id AND obj.desativado = false")
    List<LancamentoHora> findByAtividadeId(@Param("id") int atividadeId); 

    @Query("SELECT obj FROM LancamentoHora obj " +
    "JOIN FETCH obj.usuario_responsavel " +
    "JOIN FETCH obj.atividade " +
    "WHERE obj.desativado = false")
    List<LancamentoHora> findAllAtivado();

    @Query("SELECT obj FROM LancamentoHora obj " +
    "WHERE obj.usuario_responsavel.id = :id AND obj.desativado = false")
    List<LancamentoHora> findByUsuarioId(@Param("id") int usuarioId);

    @Query("SELECT COUNT(obj) > 0 FROM LancamentoHora obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int horaLancadaId);

    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteHoraById(@Param("id") int horaLancadaId);

    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.atividade.id IN (SELECT a.id FROM Atividade a WHERE a.projeto.id = :id)")
    void deleteByProjetoId(@Param("id") int projetoId);

    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.atividade.id = :id")
    void deleteByAtividadeId(@Param("id") int atividadeId);

    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.usuario_responsavel.id = :id")
    void deleteByUsuarioId(@Param("id") int usuarioId);
} 