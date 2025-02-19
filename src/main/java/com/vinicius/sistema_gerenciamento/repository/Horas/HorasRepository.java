package com.vinicius.sistema_gerenciamento.repository.Horas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;

public interface HorasRepository extends JpaRepository<LancamentoHora, Integer>{  
    List<LancamentoHora> findByAtividadeId(int id); 

    @Query("SELECT lh FROM LancamentoHora lh WHERE lh.desativado = false")
    List<LancamentoHora> findAllAtivado();
} 