package com.vinicius.sistema_gerenciamento.dto.request.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(
    @NotNull @NotBlank String email,
    @NotNull @NotBlank String senha) {
        
        public LoginRequestDTO {
            email = (email == null || email.isBlank()) ? email : email.trim();
            senha = (senha == null || senha.isBlank()) ? senha : senha.trim();
        }
}
