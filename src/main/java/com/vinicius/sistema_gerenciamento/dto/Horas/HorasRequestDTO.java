package com.vinicius.sistema_gerenciamento.dto.Horas;

import java.time.LocalDate;

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
    LocalDate data_inicio, 

    @NotNull
    LocalDate data_fim, 

    @NotNull
    @Positive
    int atividade_id, 

    @NotNull
    @Positive
    int usuario_responsavel_id

) {
}