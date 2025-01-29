package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.model.Usuario;

@Component
public class UsuarioMapper {
    
    public Usuario paraEntity(UsuarioRequestDTO data, String hashSenha) {
        if (data == null) {
            return null;
        }

        return new Usuario(data.nome(), data.email(),  hashSenha, data.perfil());
    }
}
