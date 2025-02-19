package com.vinicius.sistema_gerenciamento.repository.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.desativado = false")
    List<Usuario> findAllAtivado();
}
