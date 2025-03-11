package com.vinicius.sistema_gerenciamento.dto.response.Error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponseDTO(
    int status,
    String message,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Schema(type = "string", format = "datetime", example = "10/03/2025 18:24")
    LocalDateTime timestamp,
    List<String> details
) {
} 