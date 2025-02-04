package com.vinicius.sistema_gerenciamento.dto.request.Horas;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record HorasRequestDTO(
    
    @Size(max = 100)
    @NotNull
    @NotBlank
    String descricao, 

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_inicio, 

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_fim, 

    @NotNull
    @Positive
    int atividade_id, 

    @NotNull
    @Positive
    int usuario_responsavel_id

) {
}