package com.vinicius.sistema_gerenciamento.dto.response.Projeto;


public record ProjetoResponseDTO(
    int id,
    String nome, 
    String descricao, 
    String data_inicio, 
    String data_fim,
    String status,
    String prioridade,
    String nomeUsuario
    ) {
}
