package com.vinicius.sistema_gerenciamento.dto.response.Atividade;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record AtividadeResponseDTO(
    int id, 
    
    String nome,

    String descricao, 

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", format = "date", example = "15/03/2025")
    LocalDate data_inicio, 

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", format = "date", example = "15/03/2025")
    LocalDate data_fim, 

    String status,

    String nomeProjeto,

    String nomeUsuario
) {
}
