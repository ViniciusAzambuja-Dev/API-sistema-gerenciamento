package com.vinicius.sistema_gerenciamento.dto.response.Horas;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record HorasResponseDTO(
    int id,
    String descricao,

    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", format = "time", example = "12:30")
    LocalTime data_inicio,
    
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", format = "time", example = "15:30")
    LocalTime data_fim,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", format = "date", example = "15/03/2025")
    LocalDateTime data_registro,
    String nomeUsuario,
    String nomeAtividade
) {
}
