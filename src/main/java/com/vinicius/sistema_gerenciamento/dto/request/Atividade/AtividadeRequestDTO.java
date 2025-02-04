package com.vinicius.sistema_gerenciamento.dto.request.Atividade;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @Size(max = 100)
    String descricao, 

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_inicio, 

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
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

    public AtividadeRequestDTO {
        nome = (nome == null || nome.isBlank()) ? nome : nome.trim();
        descricao = (descricao == null || descricao.isBlank()) ? null : descricao.trim();
    }
}