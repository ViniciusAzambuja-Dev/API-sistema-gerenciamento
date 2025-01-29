package com.vinicius.sistema_gerenciamento.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.LoginRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.UsuarioMapper;
import com.vinicius.sistema_gerenciamento.model.Usuario;
import com.vinicius.sistema_gerenciamento.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    private final AuthenticationManager authenticationManager;

    public UsuarioService( UsuarioRepository usuarioRepository, UsuarioMapper mapper, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
    }

    public void realizarLogin(LoginRequestDTO data) {
        var usuarioSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usuarioSenha);
    }

    public Usuario registrarUsuario(UsuarioRequestDTO data) {
       return this.usuarioRepository.save(mapper.paraEntity(data));
    }
}
