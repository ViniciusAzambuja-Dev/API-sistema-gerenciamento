package com.vinicius.sistema_gerenciamento.repository.Atividade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.AtividadeDetalhesDTO;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer>{
    /**
     * Retorna atividades ativas, ordenadas por data de criação (decrescente).
     * 
     * @return Lista de atividades ativas.
     */
    @Query("SELECT obj FROM Atividade obj " +
       "JOIN FETCH obj.usuario_responsavel " +
       "JOIN FETCH obj.projeto " +
       "WHERE obj.desativado = false " +
       "ORDER BY obj.data_criacao DESC")
    List<Atividade> findAllAtivado();

    /**
     * Retorna atividades ativas associadas a um projeto, ordenadas por data de criação (decrescente).
     * 
     * @param projetoId ID do projeto.
     * @return Lista de atividades ativas do projeto.
     */
    @Query("SELECT obj FROM Atividade obj " +
        "WHERE obj.projeto.id = :id " +
        "AND obj.desativado = false " +
        "ORDER BY obj.data_criacao DESC")
    List<Atividade> findByProjetoId(@Param("id") int projetoId);

    /**
     * Retorna atividades ativas associadas a um usuário, ordenadas por data de criação (decrescente).
     * 
     * @param usuarioId ID do usuário.
     * @return Lista de atividades ativas do usuário.
     */
    @Query("SELECT obj FROM Atividade obj " +
        "JOIN UsuarioAtividade ua ON obj.id = ua.atividade.id " +
        "WHERE ua.usuario.id = :id " + 
        "AND obj.desativado = false " + 
        "ORDER BY obj.data_criacao DESC")
    List<Atividade> findByUsuarioId(@Param("id") int usuarioId);

    /**
     * Retorna atividades ativas dentro de um período específico, ordenadas por data de início.
     * 
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @return Lista de atividades ativas no período especificado.
     */
    @Query("SELECT obj FROM Atividade obj WHERE " +
        "obj.data_inicio >= :periodoInicial  " +
        "AND obj.data_fim <= :periodoFinal " +
        "AND obj.desativado = false " +
        "ORDER BY obj.data_inicio")
    List<Atividade> findByPeriodo(
        @Param("periodoInicial") LocalDate periodoInicial,
        @Param("periodoFinal") LocalDate periodoFinal
    );

    /**
     * Retorna detalhes de uma atividade específica, incluindo soma de horas trabalhadas.
     * 
     * @param atividadeId ID da atividade.
     * @return Detalhes da atividade encapsulados em um DTO.
     */
    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.AtividadeDetalhesDTO( " +
       "obj.id, obj.nome, obj.data_inicio, obj.data_fim, obj.status, " +
       "COUNT(DISTINCT ua.usuario.id), " +
       "SUM(CASE WHEN lh.desativado = false THEN TIMESTAMPDIFF(MINUTE, lh.data_inicio, lh.data_fim) / 60 END)) " +
       "FROM Atividade obj " +
       "LEFT JOIN LancamentoHora lh ON obj.id = lh.atividade.id " +
       "LEFT JOIN UsuarioAtividade ua ON obj.id = ua.atividade.id " +
       "WHERE obj.id = :id " +
       "AND obj.desativado = false " +
       "GROUP BY obj.id")
    Optional<AtividadeDetalhesDTO> findAtividadeDetalhes(@Param("id") int atividadeId); 

    /**
     * Retorna dados do gráfico Doughnut de status de atividades para um usuário específico.
     * 
     * @param usuarioId ID do usuário.
     * @return Lista de DTOs com contagem de atividades por status.
     */
    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO(obj.status, COUNT(obj)) " +
        "FROM Atividade obj " +
        "JOIN UsuarioAtividade ua ON obj.id = ua.atividade.id " +
        "WHERE ua.usuario.id = :id " +
        "AND obj.desativado = false " +
        "GROUP BY obj.status")
    List<GraficoDoughnutDTO> findByStatusAndUsuario(@Param("id") int usuarioId);
    
    /**
     * Retorna a quantidade de atividades com um status específico em um determinado mês.
     * 
     * @param status Status das atividades.
     * @param mes Mês de referência.
     * @return Quantidade de atividades com o status especificado no mês.
     */
    @Query("SELECT COUNT(obj) FROM Atividade obj WHERE obj.status = :status " +
        "AND (MONTH(obj.data_inicio) = :mes " +
        "OR MONTH(obj.data_fim) = :mes) " +
        "AND obj.desativado = false " )
    Integer countByStatus(@Param("status") String status, @Param("mes") int mes);
    
    /**
     * Retorna a quantidade de atividades com um status específico associadas a um usuário.
     * 
     * @param status Status das atividades.
     * @param usuarioId ID do usuário.
     * @return Quantidade de atividades com o status especificado associadas ao usuário.
     */
    @Query("SELECT COUNT(obj) FROM Atividade obj JOIN UsuarioAtividade ua " + 
        "ON obj.id = ua.atividade.id " + 
        "WHERE ua.usuario.id = :id " + 
        "AND obj.status = :status " +
        "AND obj.desativado = false")
    Integer countByStatusAndUsuario(@Param("status") String status, @Param("id") int usuarioId);

    /**
     * Verifica se uma atividade específica está ativa.
     * 
     * @param atividadeId ID da atividade.
     * @return true se a atividade estiver ativa, false caso contrário.
     */
    @Query("SELECT COUNT(obj) > 0 FROM Atividade obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int atividadeId);

    /**
     * Desativa todas as atividades associadas a um projeto.
     * 
     * @param projetoId ID do projeto.
     */
    @Modifying
    @Query("UPDATE Atividade obj SET obj.desativado = true WHERE obj.projeto.id = :id")
    void deleteByProjetoId(@Param("id") int projetoId);

    /**
     * Desativa uma atividade pelo ID.
     * 
     * @param atividadeId ID da atividade.
     */
    @Modifying
    @Query("UPDATE Atividade obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteAtividadeById(@Param("id") int atividadeId);
}