package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;

@Component
public class ProjetoMapper {
    
    public ProjetoResponseDTO paraDTO(Projeto projeto) {
        if (projeto == null) {
            return null;
        }

        String dataInicioFormatada = projeto.formataData(projeto.getData_inicio());
        String dataFimFormatada = projeto.formataData(projeto.getData_fim());

        return new ProjetoResponseDTO(
            projeto.getId(),
            projeto.getNome(), 
            projeto.getDescricao(), 
            dataInicioFormatada,
            dataFimFormatada,
            projeto.getStatus(), 
            projeto.getPrioridade(), 
            projeto.getUsuario_responsavel().getNome());
    }

    public Projeto paraEntity(ProjetoRequestDTO data, Usuario usuario) {
        if (data == null) {
            return null;
        }

        return new Projeto(data.nome(), data.descricao(), data.data_inicio(), data.data_fim(), data.status(), data.prioridade(), usuario);
    }

    public Projeto atualizaParaEntity(Projeto projeto, ProjetoUpdateDTO data) {
        projeto.setNome(data.nome());
        projeto.setDescricao(data.descricao());
        projeto.setData_inicio(data.data_inicio());
        projeto.setData_fim(data.data_fim());
        projeto.setPrioridade(data.prioridade());
        projeto.setStatus(data.status());

        return projeto;
    }
}
