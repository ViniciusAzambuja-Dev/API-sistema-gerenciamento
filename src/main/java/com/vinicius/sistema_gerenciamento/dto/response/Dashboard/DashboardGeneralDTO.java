package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

import java.util.List;

public record DashboardGeneralDTO(
    int projetosPendentes,
    int atividadesPendentes,
    int totalHorasPorMes,
    List<ProjetoPorPrioridadeDTO> projPorPrioridade,
    List<ProjetoPorStatusDTO> projPorStatus,
    List<AtividadePorStatusDTO> ativPorStatus
) {
}