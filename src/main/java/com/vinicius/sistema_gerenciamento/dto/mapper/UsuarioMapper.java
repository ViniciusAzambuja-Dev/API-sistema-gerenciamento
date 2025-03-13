package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

@Component
public class UsuarioMapper {
    
    public UsuarioResponseDTO paraDTO(Usuario usuario) {
        String dataLoginFormatada = usuario.formataData();

        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(), 
            usuario.getEmail(),
            usuario.getPerfil(),
            dataLoginFormatada);
    }

    public Usuario paraEntity(UsuarioRequestDTO data, String hashSenha) {
        return new Usuario(
            data.nome(), 
            data.email(),  
            hashSenha, 
            data.perfil()
        );
    }
    
    public Usuario atualizaParaEntity(Usuario usuario, UsuarioUpdateDTO data, String hashSenha) {
        usuario.setNome(data.nome());
        usuario.setSenha(hashSenha);
        usuario.setPerfil(data.perfil());

        return usuario;
    }
}
