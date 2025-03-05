package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

public record GraficoBarrasDTO(
    int projetoId,
    String nomeProjeto,
    Long totalHoras 
) {
}
