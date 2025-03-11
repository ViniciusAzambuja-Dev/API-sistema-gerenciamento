package com.vinicius.sistema_gerenciamento.dto.request.Projeto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProjetoUpdateDTO(
    
    @Size(max = 50)
    @NotNull
    @NotBlank
    String nome, 

    @Size(max = 200)
    String descricao,

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", format = "date", example = "15/03/2025")
    LocalDate data_inicio, 

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(type = "string", format = "date", example = "15/03/2025")
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
    int projetoId,

    @NotNull
    @Positive
    int usuarioId) {

        public ProjetoUpdateDTO {
            nome = (nome == null || nome.isBlank()) ? nome : nome.trim();
            descricao = (descricao == null || descricao.isBlank()) ? null : descricao.trim();
        }
}