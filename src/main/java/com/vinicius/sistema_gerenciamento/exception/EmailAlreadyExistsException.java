package com.vinicius.sistema_gerenciamento.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailAlreadyExistsException() {
        super("Email em uso!");
    }
}
