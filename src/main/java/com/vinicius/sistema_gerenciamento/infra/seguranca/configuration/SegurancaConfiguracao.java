package com.vinicius.sistema_gerenciamento.infra.seguranca.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração de segurança da aplicação, responsável por definir políticas de autenticação, autorização e proteção de rotas.
 */
@Configuration
@EnableWebSecurity
public class SegurancaConfiguracao {

    private final SegurancaFiltro segurancaFiltro;

    public SegurancaConfiguracao(SegurancaFiltro segurancaFiltro) {
        this.segurancaFiltro = segurancaFiltro;
    }

    /**
     * Configura a cadeia de filtros de segurança para a aplicação.
     *
     * @param httpSecurity Configuração de segurança HTTP.
     * @return SecurityFilterChain configurado.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                    .requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-resources"
                    ).permitAll()

                    //Rota de Login
                    .requestMatchers(HttpMethod.POST, "/api/usuarios/auth/login").permitAll()
                    
                    //Rotas protegidas de requisições para Usuario
                    .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/usuarios/listar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/usuarios/listar/integrantes/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/usuarios/deletar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/usuarios/atualizar").hasRole("ADMIN")

                     //Rotas protegidas de requisições para Projetos
                    .requestMatchers(HttpMethod.POST, "/api/projetos/registrar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/projetos/listar").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/projetos/listar/usuario/{id}").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.DELETE, "/api/projetos/deletar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/projetos/atualizar").hasRole("ADMIN")

                    //Rotas protegidas de requisições para Atividades
                    .requestMatchers(HttpMethod.POST, "/api/atividades/registrar").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/atividades/listar").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/atividades/listar/usuario/{id}").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/atividades/listar/projeto/{id}").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.DELETE, "/api/atividades/deletar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/atividades/atualizar").hasRole("ADMIN")

                    //Rotas protegidas de requisições para Horas lançadas
                    .requestMatchers(HttpMethod.POST, "/api/horas/registrar").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/horas/listar").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/horas/listar/atividade/{id}").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/horas/listar/usuario/{id}").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.GET, "/api/horas/listar/mes/usuario/{id}").hasAnyRole("ADMIN", "USUARIO")
                    .requestMatchers(HttpMethod.DELETE, "/api/horas/deletar/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/horas/atualizar").hasAnyRole("ADMIN", "USUARIO")

                    //Rotas protegidas de requisições para Dashboard
                    .requestMatchers(HttpMethod.GET, "/api/dashboard/dados/admin").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/dashboard/dados/gerais/{id}").hasAnyRole("ADMIN", "USUARIO")
                    
                    //Rotas protegidas de requisições para Relatorio
                    .requestMatchers(HttpMethod.GET, "/api/relatorio/projetos/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/relatorio/atividades/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/relatorio/periodo/projetos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/relatorio/periodo/atividades").hasRole("ADMIN")  
                    .requestMatchers(HttpMethod.GET, "/api/relatorio/periodo/horas").hasRole("ADMIN")                  
                    .anyRequest().authenticated()
                )
                .addFilterBefore(segurancaFiltro, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configura o gerenciador de autenticação.
     *
     * @param authenticationConfiguration Configuração de autenticação.
     * @return AuthenticationManager configurado.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configura o codificador de senhas (BCrypt).
     *
     * @return PasswordEncoder configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
