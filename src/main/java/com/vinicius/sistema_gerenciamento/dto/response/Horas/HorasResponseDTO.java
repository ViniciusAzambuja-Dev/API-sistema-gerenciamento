package com.vinicius.sistema_gerenciamento.dto.response.Horas;


public record HorasResponseDTO(
    int id,
    String descricao,
    String data_inicio,
    String data_fim,
    String data_registro,
    String nomeUsuario,
    String nomeAtividade
) {
}
