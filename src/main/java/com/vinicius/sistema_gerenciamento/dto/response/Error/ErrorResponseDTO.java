package com.vinicius.sistema_gerenciamento.dto.response.Error;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
    int status,
    String message,
    LocalDateTime timestamp,
    List<String> details
) {
} 