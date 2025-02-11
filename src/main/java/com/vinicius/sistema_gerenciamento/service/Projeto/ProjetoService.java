package com.vinicius.sistema_gerenciamento.service.Projeto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.ProjetoMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.exception.DataBaseException;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;

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
                                            projeto.getUsuario_responsavel().getId(),
                                            projeto.getUsuario_responsavel().getNome(),
                                            projeto.getUsuario_responsavel().getPerfil()
                                        )))
                                .collect(Collectors.toList());
    }

    public void deletarProjeto(int id) {
         try {
            if (!projetoRepository.existsById(id)) {
                throw new RecordNotFoundException(id);
            }
            projetoRepository.deleteById(id);

        } catch (DataIntegrityViolationException exception) {
            throw new DataBaseException();
        }
    }

    public void atualizarProjeto(int id, ProjetoRequestDTO data) {
        projetoRepository.findById(id)
            .map(projeto -> {
                if (projeto.getUsuario_responsavel().getId() != data.usuario_responsavel_id()) {
                    Usuario usuario = usuarioRepository.findById(data.usuario_responsavel_id())
                                                        .orElseThrow(() -> new RecordNotFoundException(data.usuario_responsavel_id()));
                    projeto.setUsuario_responsavel(usuario);
                }

                projetoRepository.save(mapper.atualizaParaEntity(projeto, data));
                return true;
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
