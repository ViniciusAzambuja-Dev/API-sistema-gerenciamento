package com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto;

import java.util.List;

import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;

public record RelatorioProjetoDTO(
    List<AtividadeResponseDTO> atividades,
    List<GraficoBarrasDTO> dadosGraficoBarras,
    ProjetoDetalhesDTO detalhes
) {
}