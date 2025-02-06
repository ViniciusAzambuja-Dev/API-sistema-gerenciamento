package com.vinicius.sistema_gerenciamento.exception;

public class UnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        super("Credenciais inválidas");
    }
}
