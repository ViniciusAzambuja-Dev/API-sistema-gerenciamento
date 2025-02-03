package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeResponseDTO;
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

    public AtividadeResponseDTO paraDTO(Atividade atividade, UsuarioResponseDTO usuario) {
        if (atividade == null) {
            return null;
        }

        return new AtividadeResponseDTO(
            atividade.getNome(),
            atividade.getDescricao(), 
            atividade.getData_inicio(), 
            atividade.getData_fim(), 
            atividade.getStatus(),
            usuario
        );
    }

    public Atividade atualizaParaEntity(Atividade atividade, AtividadeRequestDTO data) {
        atividade.setNome(data.nome());
        atividade.setDescricao(data.descricao());
        atividade.setData_inicio(data.data_inicio());
        atividade.setData_fim(data.data_fim());
        atividade.setStatus(data.status());

        return atividade;
    }
}
