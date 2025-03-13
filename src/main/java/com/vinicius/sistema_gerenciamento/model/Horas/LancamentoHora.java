package com.vinicius.sistema_gerenciamento.model.Horas;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lancamentos_horas")
public class LancamentoHora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 200)
    @NotNull
    @NotBlank
    @Column(length = 200, nullable = false)
    private String descricao;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime data_inicio;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime data_fim;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime data_registro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atividade", nullable = false)
    private Atividade atividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario_responsavel;

    private boolean desativado = false;

    public LancamentoHora() {
    }

    public LancamentoHora(String descricao, LocalTime data_inicio, LocalTime data_fim, Atividade atividade, Usuario usuario_responsavel) {
        this.descricao = descricao;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.atividade = atividade;
        this.usuario_responsavel = usuario_responsavel;
    }
}