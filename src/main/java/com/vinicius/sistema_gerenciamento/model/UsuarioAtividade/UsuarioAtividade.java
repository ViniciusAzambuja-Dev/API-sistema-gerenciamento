package com.vinicius.sistema_gerenciamento.model.UsuarioAtividade;

import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
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
public class UsuarioAtividade {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_atividade" , nullable = false)
    private Atividade atividade;

    public UsuarioAtividade(){}

    public UsuarioAtividade(Usuario usuario, Atividade atividade) {
        this.usuario = usuario;
        this.atividade = atividade;
    }
}
