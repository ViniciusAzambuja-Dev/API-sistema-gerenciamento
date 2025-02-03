package com.vinicius.sistema_gerenciamento.dto.Atividade;

import java.time.LocalDate;

import com.vinicius.sistema_gerenciamento.dto.UsuarioResponseDTO;

public record AtividadeResponseDTO(
    String nome,

    String descricao, 

    LocalDate data_inicio, 

    LocalDate data_fim, 

    String status,

    UsuarioResponseDTO usuario
) {
}
