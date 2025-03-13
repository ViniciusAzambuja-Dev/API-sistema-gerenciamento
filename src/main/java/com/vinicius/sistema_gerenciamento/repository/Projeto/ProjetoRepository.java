package com.vinicius.sistema_gerenciamento.repository.Projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.ProjetoDetalhesDTO;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

   
    /**
     * Retorna todos os projetos ativos, ordenados por data de criação (decrescente).
     * 
     * @return Lista de projetos ativos ordenados por data de criação.
     */
    @Query("SELECT obj FROM Projeto obj " +
        "JOIN FETCH obj.usuario_responsavel " +
        "WHERE obj.desativado = false " + 
        "ORDER BY obj.data_criacao DESC")
    List<Projeto> findAllAtivado();

    /**
     * Retorna projetos ativos associados a um usuário, ordenados por data de criação (decrescente).
     * 
     * @param usuarioId ID do usuário.
     * @return Lista de projetos ativos associados ao usuário.
     */
    @Query("SELECT obj FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id "+ 
        "AND obj.desativado = false " +
        "ORDER BY obj.data_criacao DESC")
    List<Projeto> findByUsuarioId(@Param("id") int usuarioId);

    /**
     * Retorna projetos ativos dentro de um período específico, ordenados por data de início.
     * 
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @return Lista de projetos ativos no período especificado.
     */
    @Query("SELECT obj FROM Projeto obj WHERE " +
        "obj.data_inicio >= :periodoInicial  " +
        "AND obj.data_fim <= :periodoFinal " +
        "AND obj.desativado = false " +
        "ORDER BY obj.data_inicio")
    List<Projeto> findByPeriodo(
        @Param("periodoInicial") LocalDate periodoInicial,
        @Param("periodoFinal") LocalDate periodoFinal
    );

     /**
     * Retorna detalhes de um projeto específico, incluindo métricas de atividades e horas trabalhadas.
     * 
     * @param projetoId ID do projeto.
     * @return Detalhes do projeto, encapsulados em um DTO.
     */
    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.ProjetoDetalhesDTO( " +
       "obj.id, obj.nome, obj.data_inicio, obj.data_fim, obj.status, " +
       "COUNT(DISTINCT up.usuario.id), " +
       "SUM(CASE WHEN lh.desativado = false THEN TIMESTAMPDIFF(MINUTE, lh.data_inicio, lh.data_fim) / 60 END), " +
       "COUNT(DISTINCT CASE WHEN a.desativado = false THEN a.id END), " +
       "COUNT(DISTINCT CASE WHEN a.desativado = false AND a.status = 'CONCLUIDA' THEN a.id END), " +
       "COUNT(DISTINCT CASE WHEN a.desativado = false AND a.status = 'EM_ANDAMENTO' THEN a.id END), " +
       "COUNT(DISTINCT CASE WHEN a.desativado = false AND a.status = 'ABERTA' THEN a.id END), " +
       "COUNT(DISTINCT CASE WHEN a.desativado = false AND a.status = 'PAUSADA' THEN a.id END)) " +
       "FROM Projeto obj " +
       "LEFT JOIN Atividade a ON obj.id = a.projeto.id " +
       "LEFT JOIN LancamentoHora lh ON a.id = lh.atividade.id " +
       "LEFT JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
       "WHERE obj.id = :id " +
       "AND obj.desativado = false " +
       "GROUP BY obj.id")
    Optional<ProjetoDetalhesDTO> findProjetoDetalhes(@Param("id") int projetoId); 

    /**
     * Retorna dados do gráfico Doughnut de status no dashboard.
     * 
     * @param usuarioId ID do usuário.
     * @return Lista de DTOs com contagem de projetos por status.
     */
    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO(obj.status, COUNT(obj)) " +
        "FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id " +
        "AND obj.desativado = false " +
        "GROUP BY obj.status")
    List<GraficoDoughnutDTO> findByStatusAndUsuario(@Param("id") int usuarioId);

    /**
     * Retorna dados do gráfico Doughnut de prioridade no dashboard.
     * 
     * @param usuarioId ID do usuário.
     * @return Lista de DTOs com contagem de projetos por prioridade.
     */
    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO(obj.prioridade, COUNT(obj)) " +
        "FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id " +
        "AND obj.desativado = false " +
        "GROUP BY obj.prioridade")
    List<GraficoDoughnutDTO> findByPrioridadeAndUsuario(@Param("id") int usuarioId);

    /**
     * Retorna a quantidade de projetos com um status específico.
     * 
     * @param status Status dos projetos.
     * @return Quantidade de projetos com o status especificado.
     */
    @Query("SELECT COUNT(obj) FROM Projeto obj WHERE obj.status = :status " +
    "AND obj.desativado = false")
    Integer countByStatus(@Param("status") String status);

    /**
     * Retorna a quantidade de projetos com um status específico associados a um usuário.
     * 
     * @param status Status dos projetos.
     * @param usuarioId ID do usuário.
     * @return Quantidade de projetos com o status especificado associados ao usuário.
     */
    @Query("SELECT COUNT(obj) FROM Projeto obj JOIN UsuarioProjeto up " + 
        "ON obj.id = up.projeto.id " + 
        "WHERE up.usuario.id = :id " + 
        "AND obj.status = :status " +
        "AND obj.desativado = false")
    Integer countByStatusAndUsuario(@Param("status") String status, @Param("id") int usuarioId);

    /**
     * Verifica se um projeto específico está ativo.
     * 
     * @param projetoId ID do projeto.
     * @return true se o projeto estiver ativo, false caso contrário.
     */
    @Query("SELECT COUNT(obj) > 0 FROM Projeto obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int projetoId);

    /**
     * Desativa um projeto pelo ID.
     * 
     * @param projetoId ID do projeto a ser desativado.
     */
    @Modifying
    @Query("UPDATE Projeto obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteProjetoById(@Param("id") int projetoId);
}