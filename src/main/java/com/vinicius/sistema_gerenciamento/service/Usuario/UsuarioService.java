package com.vinicius.sistema_gerenciamento.service.Usuario;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.UsuarioMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.LoginRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.exception.EmailAlreadyExistsException;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.exception.UnauthorizedException;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.Autenticacao.TokenService;
import com.vinicius.sistema_gerenciamento.service.SoftDelete.SoftDeleteService;


@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final SoftDeleteService softDeleteService;

    public UsuarioService(
        UsuarioRepository usuarioRepository, 
        TokenService tokenService, 
        AuthenticationManager authenticationManager,
        UsuarioMapper mapper,
        SoftDeleteService softDeleteService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.softDeleteService = softDeleteService;
    }

    public String realizarLogin(LoginRequestDTO data) {
        try {
            var usuarioSenha = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
            var auth = this.authenticationManager.authenticate(usuarioSenha);

            Usuario usuario = (Usuario)auth.getPrincipal();
            if (usuario.isDesativado()) {
                throw new UnauthorizedException();
            }
            usuario.setUltimo_login(LocalDateTime.now());
            usuarioRepository.save(usuario);

            var token = tokenService.gerarToken(usuario);
            return token;
        } catch (AuthenticationException exception) {
            throw new UnauthorizedException();
        }
    }

    public void registrarUsuario(UsuarioRequestDTO data) {
        if (this.usuarioRepository.findByEmail(data.email()) != null) {
            throw new EmailAlreadyExistsException();
        }

        String hashSenha = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = mapper.paraEntity(data, hashSenha);
        
        this.usuarioRepository.save(novoUsuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAllAtivado()
                                .stream()
                                .map(usuario -> mapper.paraDTO(usuario))
                                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> listarIntegrantesDoProjeto(int id) {
        return usuarioRepository.findIntegrantesByProjeto(id)
            .stream()
            .map(usuario -> mapper.paraDTO(usuario))
            .collect(Collectors.toList());
    }

    public void atualizaUsuario(UsuarioUpdateDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

            if (!data.email().equals(usuario.getEmail())) {
                if (usuarioRepository.findByEmail(data.email()) != null) {
                    throw new EmailAlreadyExistsException();   
                }
                usuario.setEmail(data.email());
            }

        String hashSenha = new BCryptPasswordEncoder().encode(data.senha());
        usuarioRepository.save(mapper.atualizaParaEntity(usuario, data, hashSenha));
    }

    public void softDeleteUsuario(int id) {
        if (!usuarioRepository.existsByIdAndAtivado(id)) {
            throw new RecordNotFoundException(id);
        }
        softDeleteService.softDeleteUsuario(id);
    }
}
