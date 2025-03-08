package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

import java.util.List;

import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO;

public record DashboardGeneralDTO(
    int projetosPendentes,
    int atividadesPendentes,
    int totalHorasPorMes,
    List<GraficoDoughnutDTO> projPorPrioridade,
    List<GraficoDoughnutDTO> projPorStatus,
    List<GraficoDoughnutDTO> ativPorStatus
) {
}