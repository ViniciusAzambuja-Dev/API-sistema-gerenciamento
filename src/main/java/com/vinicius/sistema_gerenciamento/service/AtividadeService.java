package com.vinicius.sistema_gerenciamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeResponseDTO;
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

    public List<AtividadeResponseDTO> listarAtividades() {
        return atividadeRepository.findAll()
                            .stream()
                            .map(atividade -> 
                                    mapper.paraDTO(atividade,
                                        new UsuarioResponseDTO(
                                        atividade.getUsuario_responsavel().getNome(),
                                        atividade.getUsuario_responsavel().getPerfil()
                                        )))
                                .collect(Collectors.toList());
    }
}
