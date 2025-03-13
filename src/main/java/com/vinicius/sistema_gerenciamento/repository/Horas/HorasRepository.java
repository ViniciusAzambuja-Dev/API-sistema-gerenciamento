package com.vinicius.sistema_gerenciamento.repository.Horas;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;
import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;

public interface HorasRepository extends JpaRepository<LancamentoHora, Integer>{  
    /**
     * Retorna todos os lançamentos de horas ativos, ordenados por data de registro (decrescente).
     * 
     * @return Lista de lançamentos de horas ativos.
     */
    @Query("SELECT obj FROM LancamentoHora obj " +
        "JOIN FETCH obj.usuario_responsavel " +
        "JOIN FETCH obj.atividade " +
        "WHERE obj.desativado = false " +
        "ORDER BY obj.data_registro DESC")
    List<LancamentoHora> findAllAtivado();

    /**
     * Retorna lançamentos de horas associados a uma atividade, ordenados por data de registro (decrescente).
     * 
     * @param atividadeId ID da atividade.
     * @return Lista de lançamentos de horas ativos da atividade.
     */
    @Query("SELECT obj FROM LancamentoHora obj " +
        "WHERE obj.atividade.id = :id " + 
        "AND obj.desativado = false " +
        "ORDER BY obj.data_registro DESC")
    List<LancamentoHora> findByAtividadeId(@Param("id") int atividadeId); 

    /**
     * Retorna lançamentos de horas de um usuário em um mês específico, ordenados por data de registro (decrescente).
     * 
     * @param mes Mês de referência.
     * @param usuarioId ID do usuário.
     * @return Lista de lançamentos de horas ativos do usuário no mês.
     */
    @Query("SELECT obj FROM LancamentoHora obj " +
        "JOIN FETCH obj.usuario_responsavel " +
        "JOIN FETCH obj.atividade " +
        "WHERE obj.usuario_responsavel.id = :id " + 
        "AND MONTH(obj.data_registro) = :mes " + 
        "AND obj.desativado = false " + 
        "ORDER BY obj.data_registro DESC")
    List<LancamentoHora> findAllPorMesAndUsuario(@Param("mes") int mes, @Param("id") int usuarioId);

    /**
     * Retorna todos os lançamentos de horas ativos de um usuário, ordenados por data de registro (decrescente).
     * 
     * @param usuarioId ID do usuário.
     * @return Lista de lançamentos de horas ativos do usuário.
     */
    @Query("SELECT obj FROM LancamentoHora obj " +
        "WHERE obj.usuario_responsavel.id = :id " + 
        "AND obj.desativado = false " +
        "ORDER BY obj.data_registro DESC")
    List<LancamentoHora> findByUsuarioId(@Param("id") int usuarioId);

    /**
     * Retorna lançamentos de horas ativos dentro de um período específico, opcionalmente filtrados por usuário.
     * 
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @param usuarioId ID do usuário (opcional).
     * @return Lista de lançamentos de horas ativos no período.
     */
    @Query("SELECT obj FROM LancamentoHora obj WHERE " +
        "obj.data_registro >= :periodoInicial  " +
        "AND obj.data_registro <= :periodoFinal " +
        "AND (:id IS NULL OR obj.usuario_responsavel.id = :id) " +
        "AND obj.desativado = false " +
        "ORDER BY obj.data_registro")
    List<LancamentoHora> findByPeriodo(
        @Param("periodoInicial") LocalDateTime periodoInicial,
        @Param("periodoFinal") LocalDateTime periodoFinal,
        @Param("id") Integer usuarioId
    );

    /**
     * Retorna a soma total de horas lançadas em um mês específico.
     * 
     * @param mes Mês de referência.
     * @return Soma total de horas lançadas no mês.
     */
    @Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, data_inicio, data_fim)) / 60 " +
        "FROM LancamentoHora obj WHERE MONTH(obj.data_registro) = :mes " +
        "AND obj.desativado = false")
    Double sumTotalHorasPorMes(@Param("mes") int mes);

    /**
     * Retorna a soma de horas lançadas por projeto, encapsulada em um DTO.
     * 
     * @return Lista de DTOs com a soma de horas por projeto.
     */
    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO(p.id, p.nome, SUM(TIMESTAMPDIFF(MINUTE, obj.data_inicio, obj.data_fim)) / 60) " +
        "FROM LancamentoHora obj " +
        "JOIN obj.atividade a " +
        "JOIN a.projeto p " +
        "WHERE p.desativado = false " +
        "AND a.desativado = false " +
        "AND obj.desativado = false " +
        "GROUP BY p.id, p.nome")
    List<GraficoBarrasDTO> sumHorasPorProjeto();

    /**
     * Retorna a soma de horas lançadas por atividade de um projeto específico, encapsulada em um DTO.
     * 
     * @param projetoId ID do projeto.
     * @return Lista de DTOs com a soma de horas por atividade.
     */
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

    /**
     * Retorna a soma de horas lançadas por um usuário em um mês específico.
     * 
     * @param mes Mês de referência.
     * @param usuarioId ID do usuário.
     * @return Soma de horas lançadas pelo usuário no mês.
     */
    @Query("SELECT SUM(TIMESTAMPDIFF(MINUTE, data_inicio, data_fim)) / 60 " +
        "FROM LancamentoHora obj " +
        "WHERE obj.usuario_responsavel.id = :id " + 
        "AND MONTH(obj.data_registro) = :mes " +
        "AND obj.desativado = false")
    Double sumHorasPorMesAndUsuario(@Param("mes") int mes, @Param("id") int usuarioId);

    /**
     * Verifica se um lançamento de horas específico está ativo.
     * 
     * @param horaLancadaId ID do lançamento de horas.
     * @return true se o lançamento estiver ativo, false caso contrário.
     */
    @Query("SELECT COUNT(obj) > 0 FROM LancamentoHora obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int horaLancadaId);

    /**
     * Desativa um lançamento de horas pelo ID.
     * 
     * @param horaLancadaId ID do lançamento de horas.
     */
    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteHoraById(@Param("id") int horaLancadaId);

    /**
     * Desativa todos os lançamentos de horas associados a um projeto.
     * 
     * @param projetoId ID do projeto.
     */
    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.atividade.id IN (SELECT a.id FROM Atividade a WHERE a.projeto.id = :id)")
    void deleteByProjetoId(@Param("id") int projetoId);

    /**
     * Desativa todos os lançamentos de horas associados a uma atividade.
     * 
     * @param atividadeId ID da atividade.
     */
    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.atividade.id = :id")
    void deleteByAtividadeId(@Param("id") int atividadeId);

    /**
     * Desativa todos os lançamentos de horas associados a um usuário.
     * 
     * @param usuarioId ID do usuário.
     */
    @Modifying
    @Query("UPDATE LancamentoHora obj SET obj.desativado = true WHERE obj.usuario_responsavel.id = :id")
    void deleteByUsuarioId(@Param("id") int usuarioId);
} 