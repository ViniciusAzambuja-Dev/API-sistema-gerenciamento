package com.vinicius.sistema_gerenciamento.dto.response.Atividade;

import java.time.LocalDate;

import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;

public record AtividadeResponseDTO(
    String nome,

    String descricao, 

    LocalDate data_inicio, 

    LocalDate data_fim, 

    String status,

    UsuarioResponseDTO usuario
) {
}
