package com.vinicius.sistema_gerenciamento.dto.response.Horas;

import java.time.LocalDate;

public record HorasResponseDTO(
    int id,
    String descricao,
    LocalDate data_inicio,
    LocalDate data_fim,
    String nomeUsuario,
    String nomeAtividade
) {
}
