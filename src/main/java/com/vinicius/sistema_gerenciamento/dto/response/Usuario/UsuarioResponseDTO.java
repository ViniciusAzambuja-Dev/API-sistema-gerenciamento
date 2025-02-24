package com.vinicius.sistema_gerenciamento.dto.response.Usuario;

public record UsuarioResponseDTO(
    int id, 
    String nome, 
    String email, 
    String perfil, 
    String ultimo_login
    ) {
}