package com.vinicius.sistema_gerenciamento.dto.request.Atividade;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AtividadeRequestDTO(

    @Size(max = 50)
    @NotNull
    @NotBlank
    String nome,

    String descricao, 

    @NotNull
    LocalDate data_inicio, 

    @NotNull
    LocalDate data_fim, 

    @NotNull
    @NotBlank
    @Pattern(regexp = "ABERTA|EM_ANDAMENTO|CONCLUIDA|PAUSADA")
    String status,

    @NotNull
    @Positive
    int projeto_id,

    @NotNull
    @Positive
    int usuario_responsavel_id
) {
}