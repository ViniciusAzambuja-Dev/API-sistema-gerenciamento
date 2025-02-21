package com.vinicius.sistema_gerenciamento.repository.Horas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;

public interface HorasRepository extends JpaRepository<LancamentoHora, Integer>{  
    List<LancamentoHora> findByAtividadeId(int id); 

    @Query("SELECT lh FROM LancamentoHora lh WHERE lh.desativado = false")
    List<LancamentoHora> findAllAtivado();

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