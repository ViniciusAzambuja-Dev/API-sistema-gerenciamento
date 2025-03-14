package com.vinicius.sistema_gerenciamento.model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    @NotNull
    @NotBlank
    @Column(length = 50, nullable = false)
    private String nome;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[\\w]+@workflow\\.com$")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @NotBlank
    @Column(length = 100, nullable = false)
    private String senha;

    @NotNull
    @NotBlank
    @Pattern(regexp = "ADMIN|USUARIO")
    @Column(length = 10, nullable = false)
    private String perfil;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime data_criacao;

    @Column(nullable = true)
    private LocalDateTime ultimo_login;

    private boolean desativado = false;

    public Usuario(){
        this.ultimo_login = null;
    }

    public Usuario(String nome, String email, String senha, String perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       if (this.perfil.equals("ADMIN")) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USUARIO"));
       else return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
       return email;
    }

    public String formataData() {
        if (ultimo_login == null) {
            return "N/A";
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return formato.format(ultimo_login);
    }
}