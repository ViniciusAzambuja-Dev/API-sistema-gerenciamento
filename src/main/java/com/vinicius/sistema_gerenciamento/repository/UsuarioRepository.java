package com.vinicius.sistema_gerenciamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vinicius.sistema_gerenciamento.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
