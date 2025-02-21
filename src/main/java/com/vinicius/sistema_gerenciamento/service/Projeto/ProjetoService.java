package com.vinicius.sistema_gerenciamento.service.Projeto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.AtividadeMapper;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.mapper.ProjetoMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.Atividade.AtividadeService;
import com.vinicius.sistema_gerenciamento.service.Horas.HorasService;
import com.vinicius.sistema_gerenciamento.service.UsuarioProjeto.UsuarioProjetoService;

import jakarta.transaction.Transactional;

import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;

@Service
public class ProjetoService {
    
    private final UsuarioProjetoService usuarioProjetoService;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;
    private final ProjetoMapper projetoMapper;
    private final AtividadeMapper atividadeMapper;
    private final AtividadeService atividadeService;
    private final HorasService horasService;

    public ProjetoService(
        ProjetoRepository projetoRepository,
        UsuarioRepository usuarioRepository,
        ProjetoMapper projetoMapper,
        AtividadeMapper atividadeMapper, 
        AtividadeRepository atividadeRepository,
        UsuarioProjetoService usuarioProjetoService,
        AtividadeService atividadeService,
        HorasService horasService
        ) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
        this.projetoMapper = projetoMapper;
        this.atividadeMapper = atividadeMapper;
        this.usuarioProjetoService  = usuarioProjetoService ;
        this.atividadeService = atividadeService;
        this.horasService = horasService;
    }

    public void registrarProjeto(ProjetoRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

        Projeto projeto = projetoRepository.save(projetoMapper.paraEntity(data, usuario));
        usuarioProjetoService.registrar(projeto, data.integrantesIds());
    }

     public List<ProjetoResponseDTO> listarProjetos() {
        return projetoRepository.findAllAtivado()
            .stream()
            .map(projeto -> projetoMapper.paraDTO(projeto))
            .collect(Collectors.toList());
    }

    public List<AtividadeResponseDTO> listarAtividades(int id) {
        return atividadeRepository.findByProjetoId(id)
            .stream()
            .map(atividade -> atividadeMapper.paraDTO(atividade))
            .collect(Collectors.toList());
    }

    @Transactional
    public void softDeleteProjeto(int id) {
        if (!projetoRepository.existsByIdAndAtivado(id)) {
            throw new RecordNotFoundException(id);
        }

        horasService.softDeleteByProjetoId(id);
        atividadeService.softDeleteByProjetoId(id);
        projetoRepository.deleteProjetoById(id);
    }

    public void atualizarProjeto(int id, ProjetoRequestDTO data) {
        projetoRepository.findById(id)
            .map(projeto -> {
                if (projeto.getUsuario_responsavel().getId() != data.usuarioId()) {
                    Usuario usuario = usuarioRepository.findById(data.usuarioId())
                                                        .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));
                    projeto.setUsuario_responsavel(usuario);
                }

                projetoRepository.save(projetoMapper.atualizaParaEntity(projeto, data));
                return true;
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
