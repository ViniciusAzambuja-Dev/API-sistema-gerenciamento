package com.vinicius.sistema_gerenciamento.dto.response.Atividade;

import java.time.LocalDate;


public record AtividadeResponseDTO(
    int id, 
    
    String nome,

    String descricao, 

    LocalDate data_inicio, 

    LocalDate data_fim, 

    String status,

    String nomeUsuario
) {
}
