package com.vinicius.sistema_gerenciamento.repository.Projeto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.ChartDatasDTO;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    @Query("SELECT obj FROM Projeto obj JOIN FETCH obj.usuario_responsavel WHERE obj.desativado = false")
    List<Projeto> findAllAtivado();

    @Query("SELECT obj FROM Projeto obj " +
        "JOIN UsuarioProjeto up ON obj.id = up.projeto.id " +
        "WHERE up.usuario.id = :id AND obj.desativado = false")
    List<Projeto> findByUsuarioId(@Param("id") int usuarioId);

    @Query("SELECT new com.vinicius.sistema_gerenciamento.dto.response.Dashboard.ChartDatasDTO(obj.id, obj.nome, SUM(TIMESTAMPDIFF(MINUTE, lh.data_inicio, lh.data_fim)) / 60) " +
        "FROM Projeto obj " +
        "JOIN Atividade a ON obj.id = a.projeto.id " +
        "JOIN LancamentoHora lh ON a.id = lh.atividade.id " +
        "WHERE obj.desativado = false " +
        "AND a.desativado = false " +
        "AND lh.desativado = false " +
        "GROUP BY obj.id, obj.nome")
    List<ChartDatasDTO> sumHorasPorProjeto();

    @Query("SELECT COUNT(obj) FROM Projeto obj WHERE obj.status = :status " +
    "AND obj.desativado = false")
    Integer countByStatus(@Param("status") String status);

    @Query("SELECT COUNT(obj) FROM Projeto obj JOIN UsuarioProjeto up " + 
        "ON obj.id = up.projeto.id " + 
        "WHERE up.usuario.id = :id " + 
        "AND obj.status = :status " +
        "AND obj.desativado = false")
    Integer countByStatusAndUsuario(@Param("status") String status, @Param("id") int usuarioId);
    
    @Query("SELECT COUNT(obj) > 0 FROM Projeto obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int projetoId);

    @Modifying
    @Query("UPDATE Projeto obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteProjetoById(@Param("id") int projetoId);
}