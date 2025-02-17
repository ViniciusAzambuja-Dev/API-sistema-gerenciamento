package com.vinicius.sistema_gerenciamento.dto.request.Projeto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProjetoRequestDTO(
    
    @Size(max = 50)
    @NotNull
    @NotBlank
    String nome, 

    @Size(max = 200)
    String descricao,

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_inicio, 

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
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
    int usuarioId,

    @NotNull
    @NotEmpty
    List<Integer> integrantesIds) {

        public ProjetoRequestDTO {
            nome = (nome == null || nome.isBlank()) ? nome : nome.trim();
            descricao = (descricao == null || descricao.isBlank()) ? null : descricao.trim();
        }
}