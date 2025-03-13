package com.vinicius.sistema_gerenciamento.model.UsuarioProjeto;

import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NotNull
@Entity
public class UsuarioProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_projeto" , nullable = false)
    private Projeto projeto;

    public UsuarioProjeto(){}

    public UsuarioProjeto(Usuario usuario, Projeto projeto) {
        this.usuario = usuario;
        this.projeto = projeto;
    }
}
