package com.vinicius.sistema_gerenciamento.exception;

public class DataBaseException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public DataBaseException() {
        super("Não foi possível deletar o usuário devido a restrições de integridade");
    }
}
