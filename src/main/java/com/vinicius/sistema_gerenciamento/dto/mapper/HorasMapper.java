package com.vinicius.sistema_gerenciamento.dto.mapper;

import org.springframework.stereotype.Component;

import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
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

    public HorasResponseDTO paraDTO(LancamentoHora horaLancada) {
        return new HorasResponseDTO(
            horaLancada.getDescricao(), 
            horaLancada.getData_inicio(), 
            horaLancada.getData_fim(), 
            horaLancada.getData_registro(),
            horaLancada.getUsuario_responsavel().getNome(), 
            horaLancada.getAtividade().getNome());
    }

    public LancamentoHora atualizaParaEntity(LancamentoHora horaLancada, HorasRequestDTO data) {
        horaLancada.setDescricao(data.descricao());
        horaLancada.setData_inicio(data.data_inicio());
        horaLancada.setData_fim(data.data_fim());

        return horaLancada;
    }
}
