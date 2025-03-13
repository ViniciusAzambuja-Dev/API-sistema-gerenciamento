package com.vinicius.sistema_gerenciamento.dto.request.Horas;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HorasRequestDTO(
    
    @Size(max = 200)
    @NotNull
    @NotBlank
    String descricao, 

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", format = "time", example = "12:30")
    LocalTime data_inicio, 

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    @Schema(type = "string", format = "time", example = "15:30")
    LocalTime data_fim, 

    @NotNull
    @Positive
    int atividadeId, 

    @NotNull
    @Positive
    int usuarioId

) {
    public HorasRequestDTO {
        descricao = (descricao == null || descricao.isBlank()) ? descricao : descricao.trim();
    }
}