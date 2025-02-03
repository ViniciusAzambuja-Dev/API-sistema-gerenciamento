package com.vinicius.sistema_gerenciamento.dto.response.Projeto;

import java.time.LocalDate;

import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;

public record ProjetoResponseDTO(
    String nome, 
    String descricao, 
    LocalDate data_inicio, 
    LocalDate data_fim,
    String status,
    String prioridade,
    UsuarioResponseDTO usuario
    ) {
}
