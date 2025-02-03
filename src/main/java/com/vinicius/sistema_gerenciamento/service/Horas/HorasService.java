package com.vinicius.sistema_gerenciamento.service.Horas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.HorasMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.exception.DataBaseException;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Horas.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;

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

    public List<HorasResponseDTO> listarHoras() {
        return horasRepository.findAll()
                                .stream()
                                .map(horaLancada -> 
                                        mapper.paraDTO(horaLancada)
                                )
                                .collect(Collectors.toList());
    }

    public void deletarHoras(int id) {
        try {
            if (!horasRepository.existsById(id)) {
                throw new RecordNotFoundException(id);
            }
            horasRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            throw new DataBaseException();
        }
    }

    public void atualizarHoras(int id, HorasRequestDTO data) {
        horasRepository.findById(id)
            .map(horaLancada -> {
                if (horaLancada.getUsuario_responsavel().getId() != data.usuario_responsavel_id()) {
                    Usuario usuario = usuarioRepository.findById(data.usuario_responsavel_id())
                        .orElseThrow(() -> new RecordNotFoundException(data.usuario_responsavel_id()));

                    horaLancada.setUsuario_responsavel(usuario);
                }
                if (horaLancada.getAtividade().getId() != data.atividade_id()) {
                    Atividade atividade = atividadeRepository.findById(data.atividade_id())
                        .orElseThrow(() -> new RecordNotFoundException(data.atividade_id()));

                    horaLancada.setAtividade(atividade);
                }
                
                horasRepository.save(mapper.atualizaParaEntity(horaLancada, data));
                return true;
            }).orElseThrow(() -> new RecordNotFoundException(id));
    }
}
