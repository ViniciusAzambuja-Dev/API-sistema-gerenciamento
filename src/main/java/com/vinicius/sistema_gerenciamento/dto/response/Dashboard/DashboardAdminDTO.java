package com.vinicius.sistema_gerenciamento.dto.response.Dashboard;

import java.util.List;

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
    List<GraficoBarrasDTO> chartDatas
) {
}
