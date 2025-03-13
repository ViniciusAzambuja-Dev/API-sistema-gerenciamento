package com.vinicius.sistema_gerenciamento.exception;

public class RecordNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    
    public RecordNotFoundException(int id){
        super("Registro não encontrado. " + "Id: " + id);
    }
}
