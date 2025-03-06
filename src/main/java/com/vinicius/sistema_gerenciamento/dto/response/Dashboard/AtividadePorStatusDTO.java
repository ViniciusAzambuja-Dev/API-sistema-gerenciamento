package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

public record AtividadePorStatusDTO(
    String status,
    Long totalAtividades
) {
}