package com.vinicius.sistema_gerenciamento.service;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.UsuarioMapper;
import com.vinicius.sistema_gerenciamento.model.Usuario;
import com.vinicius.sistema_gerenciamento.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;

    public UsuarioService( UsuarioRepository usuarioRepository, UsuarioMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public Usuario registrarUsuario(UsuarioRequestDTO data) {
       return this.usuarioRepository.save(mapper.paraEntity(data));
    }
}
