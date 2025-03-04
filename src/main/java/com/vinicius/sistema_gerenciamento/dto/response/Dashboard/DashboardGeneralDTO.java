package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

public record DashboardGeneralDTO(
    int projetosPendentes,
    int atividadesPendentes,
    int totalHorasPorMes
) {
}