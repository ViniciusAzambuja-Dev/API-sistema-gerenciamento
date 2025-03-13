package com.vinicius.sistema_gerenciamento.repository.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    /**
     * Busca um usuário pelo email.
     *
     * @param email O email do usuário.
     * @return UserDetails do usuário encontrado.
     */
    UserDetails findByEmail(String email);

    /**
     * Retorna uma lista de usuários ativos, ordenados pelo último login de forma decrescente.
     *
     * @return Lista de usuários ativos.
     */
    @Query("SELECT obj FROM Usuario obj " + 
    "WHERE obj.desativado = false " +
    "ORDER BY obj.ultimo_login DESC")
    List<Usuario> findAllAtivado();

    /**
     * Retorna uma lista de usuários ativos que são integrantes de um projeto específico.
     *
     * @param projetoId O ID do projeto.
     * @return Lista de usuários ativos no projeto.
     */
    @Query("SELECT obj FROM Usuario obj " +
    "JOIN UsuarioProjeto up ON obj.id = up.usuario.id " +
    "WHERE up.projeto.id = :id " +
    "AND obj.desativado = false")
    List<Usuario> findIntegrantesByProjeto(@Param("id") int projetoId);

    /**
     * Retorna a quantidade de usuários ativos.
     *
     * @return Número de usuários ativos.
     */
    @Query("SELECT COUNT(obj) FROM Usuario obj WHERE obj.desativado = false")
    Integer countUsuariosAtivos();

     /**
     * Verifica se um usuário específico está ativo.
     *
     * @param usuarioId O ID do usuário.
     * @return true se o usuário estiver ativo, false caso contrário.
     */
    @Query("SELECT COUNT(obj) > 0 FROM Usuario obj WHERE obj.id = :id AND obj.desativado = false")
    Boolean existsByIdAndAtivado(@Param("id") int usuarioId);
    
     /**
     * Desativa um usuário pelo ID.
     *
     * @param usuarioId O ID do usuário a ser desativado.
     */
    @Modifying
    @Query("UPDATE Usuario obj SET obj.desativado = true WHERE obj.id = :id")
    void deleteUsuarioById(@Param("id") int usuarioId);
}
