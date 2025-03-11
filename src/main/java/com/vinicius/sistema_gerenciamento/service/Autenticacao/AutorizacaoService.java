package com.vinicius.sistema_gerenciamento.service.Autenticacao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;

/**
 * Serviço responsável por carregar os detalhes do usuário durante o processo de autenticação.
 * Implementa a interface {@link UserDetailsService} do Spring Security.
 */
@Service
public class AutorizacaoService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public AutorizacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Carrega os detalhes do usuário com base no email fornecido.
     *
     * @param email Email do usuário a ser autenticado.
     * @return Detalhes do usuário encapsulados em um {@link UserDetails}.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email);
    }
    
}