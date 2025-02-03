package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.model.Atividade;
import com.vinicius.sistema_gerenciamento.model.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario;

@Component
public class AtividadeMapper {

    public Atividade paraEntity(AtividadeRequestDTO data, Usuario usuario, Projeto projeto) {
        if (data == null) {
            return null;
        }

        return new Atividade(data.nome(), data.descricao(), data.data_inicio(), data.data_fim(), data.status(), projeto, usuario);
    }
}
