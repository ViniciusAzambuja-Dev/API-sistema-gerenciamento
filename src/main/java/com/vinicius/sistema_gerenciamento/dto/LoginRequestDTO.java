package com.vinicius.sistema_gerenciamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
    @NotNull @NotBlank String email,
    @Size(max = 15) @NotNull @NotBlank String senha) {
}
