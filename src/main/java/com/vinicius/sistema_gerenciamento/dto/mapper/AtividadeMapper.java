package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

@Component
public class AtividadeMapper {

    public Atividade paraEntity(AtividadeRequestDTO data, Usuario usuario, Projeto projeto) {
        if (data == null) {
            return null;
        }

        return new Atividade(data.nome(), data.descricao(), data.data_inicio(), data.data_fim(), data.status(), projeto, usuario);
    }

    public AtividadeResponseDTO paraDTO(Atividade atividade) {
        if (atividade == null) {
            return null;
        }

        String dataInicioFormatada = atividade.formataData(atividade.getData_inicio());
        String dataFimFormatada = atividade.formataData(atividade.getData_fim());

        return new AtividadeResponseDTO(
            atividade.getId(),
            atividade.getNome(),
            atividade.getDescricao(), 
            dataInicioFormatada, 
            dataFimFormatada, 
            atividade.getStatus(),
            atividade.getUsuario_responsavel().getNome()
        );
    }

    public Atividade atualizaParaEntity(Atividade atividade, AtividadeUpdateDTO data) {
        atividade.setNome(data.nome());
        atividade.setDescricao(data.descricao());
        atividade.setData_inicio(data.data_inicio());
        atividade.setData_fim(data.data_fim());
        atividade.setStatus(data.status());

        return atividade;
    }
}
