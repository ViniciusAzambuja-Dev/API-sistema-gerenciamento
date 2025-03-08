package com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AtividadeDetalhesDTO(
    int id,
    String nome,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_inicio,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_fim,
    String status,
    Long quantidadeIntegrantes,
    Long totalHoras
) {
    public AtividadeDetalhesDTO {
        totalHoras = totalHoras == null ? 0 : totalHoras;
    }
}
