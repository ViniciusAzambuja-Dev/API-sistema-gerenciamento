package com.vinicius.sistema_gerenciamento.service;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.HorasMapper;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Atividade;
import com.vinicius.sistema_gerenciamento.model.Usuario;
import com.vinicius.sistema_gerenciamento.repository.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioRepository;

@Service
public class HorasService {

    private final HorasRepository horasRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;
    private final HorasMapper mapper;

    public HorasService(
        HorasRepository horasRepository, 
        UsuarioRepository usuarioRepository, 
        AtividadeRepository atividadeRepository, 
        HorasMapper mapper
    ) {

        this.horasRepository = horasRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
        this.mapper = mapper;
    }
    
    public void registrarHoras(HorasRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuario_responsavel_id())
            .orElseThrow(() -> new RecordNotFoundException(data.usuario_responsavel_id()));

        Atividade atividade = atividadeRepository.findById(data.atividade_id())
            .orElseThrow(() -> new RecordNotFoundException(data.atividade_id()));

        horasRepository.save(mapper.paraEntity(data, usuario, atividade));
    }
}
