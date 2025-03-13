package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

import java.util.List;

import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;

public record DashboardAdminDTO(
    int projetosConcluidos,
    int projetosPendentes,
    int projetosPlanejados,
    int projetosCancelados,
    int atividadesConcluidas,
    int atividadesPendentes,
    int atividadesAbertas,
    int atividadesPausadas,
    int usuariosAtivos,
    int totalHorasPorMes,
    List<GraficoBarrasDTO> dadosGraficoBarras
) {
}
