package com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade;

import java.util.List;

import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;

public record RelatorioAtividadeDTO(
    List<HorasResponseDTO> horasLancadas,
    AtividadeDetalhesDTO detalhes
) {
}
