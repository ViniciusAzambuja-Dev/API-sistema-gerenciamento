package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.model.Atividade;
import com.vinicius.sistema_gerenciamento.model.LancamentoHora;
import com.vinicius.sistema_gerenciamento.model.Usuario;

@Component
public class HorasMapper {
    
    public LancamentoHora paraEntity(HorasRequestDTO data, Usuario usuario, Atividade atividade) {
        if (data == null) {
            return null;
        }

        return new LancamentoHora(data.descricao(), data.data_inicio(), data.data_fim(), atividade, usuario);
    }
}
