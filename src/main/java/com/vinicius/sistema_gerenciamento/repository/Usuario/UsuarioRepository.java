package com.vinicius.sistema_gerenciamento.repository.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByEmail(String email);
}
