package com.vinicius.sistema_gerenciamento.dto.request.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateDTO(
    @Size(max = 50) 
    @NotNull 
    @NotBlank
    String nome,

    @NotNull 
    @NotBlank
    String email,

    @Size(max = 15) 
    @NotNull 
    @NotBlank
    String senha,
    
    @Size(max = 10) 
    @NotNull 
    @NotBlank
    String perfil,

    @NotNull
    @Positive
    int usuarioId) {
        
        public UsuarioUpdateDTO {
            nome = (nome == null || nome.isBlank()) ? nome : nome.trim();
            email = (email == null || email.isBlank()) ? email : email.trim();
            senha = (senha == null || senha.isBlank()) ? senha : senha.trim();
        }
}
