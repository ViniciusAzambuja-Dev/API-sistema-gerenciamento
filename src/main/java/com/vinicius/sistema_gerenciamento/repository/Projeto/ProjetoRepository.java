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

    @Query("SELECT obj FROM Projeto obj JOIN FETCH obj.usuario_responsavel WHERE obj.desativado = false")
    List<Projeto> findAllAtivado();

    @Query("SELECT obj FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id AND obj.desativado = false")
    List<Projeto> findByUsuarioId(@Param("id") int usuarioId);

    @Query("SELECT COUNT(obj) FROM Projeto obj WHERE obj.status = :status " +
    "AND obj.desativado = false")
    Integer countByStatus(@Param("status") String status);

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

    @Query("SELECT obj FROM Projeto obj WHERE " +
        "obj.data_inicio >= :periodoInicial  " +
        "AND obj.data_fim <= :periodoFinal " +
        "AND obj.desativado = false " +
        "ORDER BY obj.data_inicio")
    List<Projeto> findByPeriodo(
        @Param("periodoInicial") LocalDate periodoInicial,
        @Param("periodoFinal") LocalDate periodoFinal
    );

    @Query("SELECT COUNT(obj) FROM Projeto obj JOIN UsuarioProjeto up " + 
        "ON obj.id = up.projeto.id " + 
        "WHERE up.usuario.id = :id " + 
        "AND obj.status = :status " +
        "AND obj.desativado = false")
    Integer countByStatusAndUsuario(@Param("status") String status, @Param("id") int usuarioId);

    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO(obj.status, COUNT(obj)) " +
        "FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id " +
        "AND obj.desativado = false " +
        "GROUP BY obj.status")
    List<GraficoDoughnutDTO> countByStatusAndUsuario(@Param("id") int usuarioId);

    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO(obj.prioridade, COUNT(obj)) " +
        "FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id " +
        "AND obj.desativado = false " +
        "GROUP BY obj.prioridade")
    List<GraficoDoughnutDTO> countByPrioridadeAndUsuario(@Param("id") int usuarioId);
    
    @Query("SELECT COUNT(obj) > 0 FROM Projeto obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int projetoId);

    @Modifying
    @Query("UPDATE Projeto obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteProjetoById(@Param("id") int projetoId);
}