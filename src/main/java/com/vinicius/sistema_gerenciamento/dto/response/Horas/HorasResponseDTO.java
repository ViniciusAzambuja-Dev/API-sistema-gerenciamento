package com.vinicius.sistema_gerenciamento.dto.response.Horas;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record HorasResponseDTO(
    int id,
    String descricao,
    LocalDate data_inicio,
    LocalDate data_fim,
    LocalDateTime data_registro,
    String nomeUsuario,
    String nomeAtividade
) {
}
