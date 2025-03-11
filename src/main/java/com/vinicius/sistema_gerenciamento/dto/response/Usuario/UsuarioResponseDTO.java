package com.vinicius.sistema_gerenciamento.dto.response.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioResponseDTO(
    int id, 
    String nome, 
    String email, 
    String perfil, 
    @Schema(type = "string", format = "string", example = "10/03/2025 18:24")
    String ultimo_login
    ) {
}