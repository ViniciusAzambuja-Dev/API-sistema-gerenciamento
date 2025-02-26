package com.vinicius.sistema_gerenciamento.repository.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.desativado = false")
    List<Usuario> findAllAtivado();

    @Query("SELECT obj FROM Usuario obj " +
    "JOIN UsuarioProjeto up ON obj.id = up.usuario.id " +
    "WHERE up.projeto.id = :projetoId " +
    "AND obj.desativado = false")
    List<Usuario> findIntegrantesByProjeto(@Param("projetoId") int projetoId);

    @Query("SELECT COUNT(obj) > 0 FROM Usuario obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int usuarioId);
    
    @Modifying
    @Query("UPDATE Usuario obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteUsuarioById(@Param("id") int usuarioId);
}
