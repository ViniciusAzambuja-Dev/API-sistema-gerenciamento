package com.vinicius.sistema_gerenciamento.service.Atividade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.AtividadeMapper;
import com.vinicius.sistema_gerenciamento.dto.mapper.HorasMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.exception.DataBaseException;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Horas.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.UsuarioAtividade.UsuarioAtividadeService;

@Service
public class AtividadeService {
    private final AtividadeRepository atividadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final HorasRepository horaRepository;
    private final UsuarioAtividadeService usuarioAtividadeService;
    private final HorasMapper horasMapper;
    private final AtividadeMapper mapper;

    public AtividadeService(
        UsuarioAtividadeService usuarioAtividadeService,
        AtividadeRepository atividadeRepository, 
        AtividadeMapper mapper, 
        UsuarioRepository usuarioRepository, 
        ProjetoRepository projetoRepository, 
        HorasRepository horaRepository, 
        HorasMapper horasMapper
    ) {
        this.usuarioAtividadeService = usuarioAtividadeService;
        this.atividadeRepository = atividadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.horaRepository = horaRepository;
        this.horasMapper = horasMapper;
        this.mapper = mapper;
    }

    public void registrarAtividade(AtividadeRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

        Projeto projeto = projetoRepository.findById(data.projetoId())
            .orElseThrow(() -> new RecordNotFoundException(data.projetoId()));

        Atividade atividade = atividadeRepository.save(mapper.paraEntity(data, usuario, projeto));  
        usuarioAtividadeService.registrar(atividade, data.integrantesIds());
    }

    public List<AtividadeResponseDTO> listarAtividades() {
        return atividadeRepository.findAllAtivado()
            .stream()
            .map(atividade -> mapper.paraDTO(atividade))
            .collect(Collectors.toList());
    }

    public List<HorasResponseDTO> listarHoras(int id) {
        return horaRepository.findByAtividadeId(id)
            .stream()
            .map(hora -> horasMapper.paraDTO(hora))
            .collect(Collectors.toList());
    }

    public void atualizarAtividades(int id, AtividadeRequestDTO data) {
        atividadeRepository.findById(id)
            .map(atividade -> {
                if (atividade.getUsuario_responsavel().getId() != data.usuarioId()) {
                    Usuario usuario = usuarioRepository.findById(data.usuarioId())
                    .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));  

                    atividade.setUsuario_responsavel(usuario);
                }
                atividadeRepository.save(mapper.atualizaParaEntity(atividade, data));
                return true;
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void deletarAtividade(int id) {
        try {
            if (!atividadeRepository.existsById(id)) {
                throw new RecordNotFoundException(id);
            }
            atividadeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException();
        }
    }
}
