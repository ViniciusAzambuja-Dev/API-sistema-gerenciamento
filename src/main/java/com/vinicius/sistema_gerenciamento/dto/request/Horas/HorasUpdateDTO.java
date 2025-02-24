package com.vinicius.sistema_gerenciamento.dto.request.Horas;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HorasUpdateDTO(
    
    @Size(max = 200)
    @NotNull
    @NotBlank
    String descricao, 

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    LocalTime data_inicio, 

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    LocalTime data_fim, 

    @NotNull
    @Positive
    int horaId,

    @NotNull
    @Positive
    int atividadeId
) {
    public HorasUpdateDTO {
        descricao = (descricao == null || descricao.isBlank()) ? descricao : descricao.trim();
    }
}
