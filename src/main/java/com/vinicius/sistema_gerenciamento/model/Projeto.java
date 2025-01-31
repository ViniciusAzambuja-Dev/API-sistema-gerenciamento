package com.vinicius.sistema_gerenciamento.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    @NotNull
    @NotBlank
    @Column(length = 50, nullable = false)
    private String nome;

    @Lob
    private String descricao;

    @NotNull
    @Column(nullable = false)
    private LocalDate data_inicio;

    @NotNull
    @Column(nullable = false)
    private LocalDate data_fim;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime data_criacao;

    @NotNull
    @NotBlank
    @Pattern(regexp = "PLANEJADO|EM_ANDAMENTO|CONCLUIDO|CANCELADO")
    @Column(length = 15, nullable = false)
    private String status;

    @NotNull
    @NotBlank
    @Pattern(regexp = "ALTA|MEDIA|BAIXA")
    @Column(length = 10, nullable = false)
    private String prioridade;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_usuario_responsavel", nullable = false)
    private Usuario usuario_responsavel;

    public Projeto() {
    }

    public Projeto(String nome, String descricao, LocalDate data_inicio, LocalDate data_fim, String status, String prioridade, Usuario usuario_responsavel) {
        this.nome = nome;
        this.descricao = (descricao == null || descricao.isBlank()) ? null : descricao;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.status = status;
        this.prioridade = prioridade;
        this.usuario_responsavel = usuario_responsavel;
    }
}

