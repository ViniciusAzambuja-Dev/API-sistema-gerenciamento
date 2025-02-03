package com.vinicius.sistema_gerenciamento.dto.request.Projeto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProjetoRequestDTO(
    
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
    @Pattern(regexp = "PLANEJADO|EM_ANDAMENTO|CONCLUIDO|CANCELADO")
    String status, 

    @NotNull
    @NotBlank
    @Pattern(regexp = "ALTA|MEDIA|BAIXA")
    String prioridade, 

    @NotNull
    @Positive
    int usuario_responsavel_id) {
}