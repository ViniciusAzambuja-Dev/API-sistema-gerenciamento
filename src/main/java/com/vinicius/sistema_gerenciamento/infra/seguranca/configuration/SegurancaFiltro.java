package com.vinicius.sistema_gerenciamento.infra.seguranca.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.Autenticacao.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança personalizado que valida tokens JWT e autentica o usuário em cada requisição.
 */
@Component
public class SegurancaFiltro extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SegurancaFiltro(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método principal do filtro, executado em cada requisição.
     * Valida o token JWT presente no cabeçalho da requisição e autentica o usuário no contexto de segurança.
     *
     * @param request Requisição HTTP.
     * @param response Resposta HTTP.
     * @param filterChain Cadeia de filtros a ser executada.
     * @throws ServletException Se ocorrer um erro durante o processamento da requisição.
     * @throws IOException Se ocorrer um erro de I/O durante o processamento da requisição.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recuperarToken(request);
        if (token != null) {
            var login = tokenService.validarToken(token);
            UserDetails usuario = usuarioRepository.findByEmail(login);

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho "Authorization" da requisição.
     *
     * @param request Requisição HTTP.
     * @return Token JWT (sem o prefixo "Bearer") ou null se o cabeçalho não estiver presente.
     */
    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
