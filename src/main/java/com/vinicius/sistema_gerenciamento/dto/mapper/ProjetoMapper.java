package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.model.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario;

@Component
public class ProjetoMapper {
    
    public ProjetoResponseDTO paraDTO(Projeto projeto, UsuarioResponseDTO usuarioResponseDTO) {
        if (projeto == null) {
            return null;
        }

        return new ProjetoResponseDTO(
            projeto.getNome(), 
            projeto.getDescricao(), 
            projeto.getData_inicio(), 
            projeto.getData_fim(), 
            projeto.getStatus(), 
            projeto.getPrioridade(), 
            usuarioResponseDTO);
    }

    public Projeto paraEntity(ProjetoRequestDTO data, Usuario usuario) {
        if (data == null) {
            return null;
        }

        return new Projeto(data.nome(), data.descricao(), data.data_inicio(), data.data_fim(), data.status(), data.prioridade(), usuario);
    }

    public Projeto atualizaParaEntity(Projeto projeto, ProjetoRequestDTO data) {
        projeto.setNome(data.nome());
        projeto.setDescricao(data.descricao());
        projeto.setData_inicio(data.data_inicio());
        projeto.setData_fim(data.data_fim());
        projeto.setPrioridade(data.prioridade());
        projeto.setStatus(data.status());

        return projeto;
    }
}
