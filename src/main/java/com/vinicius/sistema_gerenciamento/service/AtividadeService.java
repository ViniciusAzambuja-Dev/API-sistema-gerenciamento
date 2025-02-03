package com.vinicius.sistema_gerenciamento.service;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.AtividadeMapper;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario;
import com.vinicius.sistema_gerenciamento.repository.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioRepository;

@Service
public class AtividadeService {
    private final AtividadeRepository atividadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final AtividadeMapper mapper;

    public AtividadeService(AtividadeRepository atividadeRepository, AtividadeMapper mapper, UsuarioRepository usuarioRepository, ProjetoRepository projetoRepository) {
        this.atividadeRepository = atividadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.mapper = mapper;
    }

    public void registrarAtividade(AtividadeRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuario_responsavel_id())
                                            .orElseThrow(() -> new RecordNotFoundException(data.usuario_responsavel_id()));

        Projeto projeto = projetoRepository.findById(data.projeto_id())
                                            .orElseThrow(() -> new RecordNotFoundException(data.projeto_id()));

        atividadeRepository.save(mapper.paraEntity(data, usuario, projeto));                             
    }
}
