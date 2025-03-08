package com.vinicius.sistema_gerenciamento.repository.Horas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;
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
    "JOIN FETCH obj.usuario_responsavel " +
    "JOIN FETCH obj.atividade " +
    "WHERE obj.usuario_responsavel.id = :id " + 
    "AND MONTH(obj.data_registro) = :mes " + 
    "AND obj.desativado = false " + 
    "ORDER BY obj.data_registro DESC")
    List<LancamentoHora> findAllPorMesAndUsuario(@Param("mes") int mes, @Param("id") int usuarioId);

    @Query("SELECT obj FROM LancamentoHora obj " +
    "WHERE obj.usuario_responsavel.id = :id AND obj.desativado = false")
    List<LancamentoHora> findByUsuarioId(@Param("id") int usuarioId);

    @Query("SELECT COUNT(obj) > 0 FROM LancamentoHora obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int horaLancadaId);

    @Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, data_inicio, data_fim)) / 60 " +
        "FROM LancamentoHora obj WHERE MONTH(obj.data_registro) = :mes " +
        "AND obj.desativado = false")
    Double sumTotalHorasPorMes(@Param("mes") int mes);

    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO(p.id, p.nome, SUM(TIMESTAMPDIFF(MINUTE, obj.data_inicio, obj.data_fim)) / 60) " +
        "FROM LancamentoHora obj " +
        "JOIN obj.atividade a " +
        "JOIN a.projeto p " +
        "WHERE p.desativado = false " +
        "AND a.desativado = false " +
        "AND obj.desativado = false " +
        "GROUP BY p.id, p.nome")
    List<GraficoBarrasDTO> sumHorasPorProjeto();

    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO( " +
        "a.id, a.nome, SUM(TIMESTAMPDIFF(MINUTE, obj.data_inicio, obj.data_fim)) / 60) " +
        "FROM LancamentoHora obj " +
        "JOIN obj.atividade a " +
        "JOIN a.projeto p " +
        "WHERE p.desativado = false " +
        "AND a.desativado = false " +
        "AND obj.desativado = false " +
        "AND p.id = :id " +
        "GROUP BY a.id, a.nome")
    List<GraficoBarrasDTO> sumHorasPorAtividade(@Param("id") int projetoId);

    @Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, data_inicio, data_fim)) / 60 " +
        "FROM LancamentoHora obj " +
        "WHERE obj.usuario_responsavel.id = :id " + 
        "AND MONTH(obj.data_registro) = :mes " +
        "AND obj.desativado = false")
    Double sumHorasPorMesAndUsuario(@Param("mes") int mes, @Param("id") int usuarioId);

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