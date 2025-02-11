package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

@Component
public class UsuarioMapper {
    
    public UsuarioResponseDTO paraDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getPerfil());
    }

    public Usuario paraEntity(UsuarioRequestDTO data, String hashSenha) {
        if (data == null) {
            return null;
        }

        return new Usuario(data.nome(), data.email(),  hashSenha, data.perfil());
    }
    
    public Usuario atualizaParaEntity(Usuario usuario, UsuarioRequestDTO data, String hashSenha) {
        usuario.setNome(data.nome());
        usuario.setSenha(hashSenha);
        usuario.setPerfil(data.perfil());

        return usuario;
    }
}
