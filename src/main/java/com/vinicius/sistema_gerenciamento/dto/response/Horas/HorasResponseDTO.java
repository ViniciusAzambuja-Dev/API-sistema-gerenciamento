package com.vinicius.sistema_gerenciamento.dto.response.Horas;

import java.time.LocalTime;

public record HorasResponseDTO(
    int id,
    String descricao,
    LocalTime data_inicio,
    LocalTime data_fim,
    String nomeUsuario,
    String nomeAtividade
) {
}
