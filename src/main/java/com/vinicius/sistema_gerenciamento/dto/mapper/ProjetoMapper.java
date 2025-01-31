package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.model.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario;

@Component
public class ProjetoMapper {
    
    public Projeto paraEntity(ProjetoRequestDTO data, Usuario usuario) {
        if (data == null) {
            return null;
        }

        return new Projeto(data.nome(), data.descricao(), data.data_inicio(), data.data_fim(), data.status(), data.prioridade(), usuario);
    }
}
