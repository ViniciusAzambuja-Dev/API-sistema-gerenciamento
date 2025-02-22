package com.vinicius.sistema_gerenciamento.dto.response.Atividade;


public record AtividadeResponseDTO(
    int id, 
    
    String nome,

    String descricao, 

    String data_inicio, 

    String data_fim, 

    String status,

    String nomeUsuario
) {
}
