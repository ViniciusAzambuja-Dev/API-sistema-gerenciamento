package com.vinicius.sistema_gerenciamento.service.Autenticacao;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

/**
 * Serviço responsável por gerar e validar tokens JWT (JSON Web Tokens) para autenticação de usuários.
 */
@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para um usuário.
     *
     * @param usuario Usuário para o qual o token será gerado.
     * @return Token JWT gerado.
     * @throws RuntimeException Se ocorrer um erro durante a geração do token.
     */
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(usuario.getEmail())
                            .withClaim("id", usuario.getId())
                            .withClaim("roles", usuario.getPerfil())
                            .withExpiresAt(gerarDataExpiracao())
                            .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token");
        }
    }

    /**
     * Valida um token JWT e retorna o subject (email do usuário) se o token for válido.
     *
     * @param token Token JWT a ser validado.
     * @return Email do usuário (subject) se o token for válido, ou uma string vazia se o token for inválido.
     */
    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    /**
     * Gera a data de expiração do token (2 horas a partir do momento atual).
     *
     * @return Instante (Instant) representando a data de expiração.
     */
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
