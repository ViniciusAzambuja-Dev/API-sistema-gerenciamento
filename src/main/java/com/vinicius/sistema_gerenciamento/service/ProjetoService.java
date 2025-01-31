package com.vinicius.sistema_gerenciamento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.ProjetoMapper;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario;
import com.vinicius.sistema_gerenciamento.repository.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioRepository;

@Service
public class ProjetoService {
    
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoMapper mapper;

    public ProjetoService(ProjetoRepository projetoRepository, UsuarioRepository usuarioRepository, ProjetoMapper mapper) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public void registrarProjeto(ProjetoRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuario_responsavel_id())
                                        .orElseThrow(() -> new RecordNotFoundException(data.usuario_responsavel_id()));

        projetoRepository.save(mapper.paraEntity(data, usuario));
    }

     public List<ProjetoResponseDTO> listarProjetos() {
        return projetoRepository.findAll()
                                .stream()
                                .map(projeto -> 
                                        mapper.paraDTO(projeto, 
                                        new UsuarioResponseDTO(
                                            projeto.getUsuario_responsavel().getNome(),
                                            projeto.getUsuario_responsavel().getPerfil()
                                        )))
                                .collect(Collectors.toList());
    }
}
