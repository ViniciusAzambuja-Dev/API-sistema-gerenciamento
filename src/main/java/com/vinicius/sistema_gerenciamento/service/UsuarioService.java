package com.vinicius.sistema_gerenciamento.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.LoginRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.UsuarioMapper;
import com.vinicius.sistema_gerenciamento.exception.DataBaseException;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.infra.seguranca.TokenService;
import com.vinicius.sistema_gerenciamento.model.Usuario;
import com.vinicius.sistema_gerenciamento.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public UsuarioService(
        UsuarioRepository usuarioRepository, 
        TokenService tokenService, 
        AuthenticationManager authenticationManager,
        UsuarioMapper mapper
    ) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String realizarLogin(LoginRequestDTO data) {
        var usuarioSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usuarioSenha);

        Usuario usuario = (Usuario)auth.getPrincipal();
        usuario.setUltimo_login(LocalDateTime.now());
        usuarioRepository.save(usuario);

        var token = tokenService.gerarToken(usuario);

        return token;
    }

    public boolean registrarUsuario(UsuarioRequestDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()) != null) {
            return false;
        }

        String hashSenha = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = mapper.paraEntity(data, hashSenha);
        
        this.usuarioRepository.save(novoUsuario);

        return true;
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                                .stream()
                                .map(usuario -> mapper.paraDTO(usuario))
                                .collect(Collectors.toList());
    }

    public void atualizaUsuario(int id, UsuarioRequestDTO data) {
        usuarioRepository.findById(id)
            .map(usuario -> {
                String hashSenha = new BCryptPasswordEncoder().encode(data.senha());
                usuario.setNome(data.nome());
                usuario.setSenha(hashSenha);
                usuario.setPerfil(data.perfil());
                
                usuarioRepository.save(usuario);
                return true;
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deletarUsuario(int id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new RecordNotFoundException(id);
            }
            usuarioRepository.deleteById(id);

        } catch (DataIntegrityViolationException exception) {
            throw new DataBaseException();
        }
    }
}
