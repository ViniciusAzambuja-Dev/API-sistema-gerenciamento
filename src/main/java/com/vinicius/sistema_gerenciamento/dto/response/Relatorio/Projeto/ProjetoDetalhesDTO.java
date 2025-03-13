package com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ProjetoDetalhesDTO(
    int id,
    String nome,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_inicio,
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate data_fim,
    String status,
    Long quantidadeIntegrantes,
    Long totalHoras,
    Long totalAtividades,
    Long atividadesConcluidas,
    Long atividadesEmAndamento,
    Long atividadesAbertas,
    Long atividadesPausadas
) {
    public ProjetoDetalhesDTO {
        totalHoras = totalHoras == null ? 0 : totalHoras;
    }
}