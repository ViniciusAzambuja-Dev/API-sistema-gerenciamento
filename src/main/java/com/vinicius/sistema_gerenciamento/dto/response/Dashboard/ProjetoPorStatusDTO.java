package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

public record ProjetoPorStatusDTO(
    String status,
    Long totalProjetos
) {
}
