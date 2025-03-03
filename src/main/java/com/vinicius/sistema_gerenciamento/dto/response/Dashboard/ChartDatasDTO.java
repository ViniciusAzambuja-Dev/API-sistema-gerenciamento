package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

public record ChartDatasDTO(
    int projetoId,
    String nomeProjeto,
    Long totalHoras 
) {
}
