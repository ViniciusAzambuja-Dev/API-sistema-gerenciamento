package com.vinicius.sistema_gerenciamento.repository.Horas;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;

public interface HorasRepository extends JpaRepository<LancamentoHora, Integer>{   
} 