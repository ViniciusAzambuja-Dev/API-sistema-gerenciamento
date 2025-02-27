package com.vinicius.sistema_gerenciamento.service.Horas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.HorasMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Horas.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.SoftDelete.SoftDeleteService;

@Service
public class HorasService {

    private final HorasRepository horasRepository;
    private final UsuarioRepository usuarioRepository;
    private final AtividadeRepository atividadeRepository;
    private final HorasMapper mapper;
    private final SoftDeleteService softDeleteService;

    public HorasService(
        HorasRepository horasRepository, 
        UsuarioRepository usuarioRepository, 
        AtividadeRepository atividadeRepository, 
        HorasMapper mapper,
        SoftDeleteService softDeleteService
    ) {

        this.horasRepository = horasRepository;
        this.usuarioRepository = usuarioRepository;
        this.atividadeRepository = atividadeRepository;
        this.mapper = mapper;
        this.softDeleteService = softDeleteService;
    }
    
    public void registrarHoras(HorasRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

        Atividade atividade = atividadeRepository.findById(data.atividadeId())
            .orElseThrow(() -> new RecordNotFoundException(data.atividadeId()));

        horasRepository.save(mapper.paraEntity(data, usuario, atividade));
    }

    public List<HorasResponseDTO> listarHoras() {
        return horasRepository.findAllAtivado()
            .stream()
            .map(horaLancada -> mapper.paraDTO(horaLancada))
            .collect(Collectors.toList());
    }

    public List<HorasResponseDTO> listarPorAtividade(int id) {
        return horasRepository.findByAtividadeId(id)
            .stream()
            .map(hora -> mapper.paraDTO(hora))
            .collect(Collectors.toList());
    }

    public void softDeleteHora(int id) {
        if (!horasRepository.existsByIdAndAtivado(id)) {
            throw new RecordNotFoundException(id);
        }
        softDeleteService.softDeleteHora(id);
    }
      
    public void atualizarHoras(HorasUpdateDTO data) {
        LancamentoHora horaLancada = horasRepository.findById(data.horaId())
            .orElseThrow(() -> new RecordNotFoundException(data.horaId()));

        if (horaLancada.getAtividade().getId() != data.atividadeId()) {
            Atividade atividade = atividadeRepository.findById(data.atividadeId())
                .orElseThrow(() -> new RecordNotFoundException(data.atividadeId()));

            horaLancada.setAtividade(atividade);
        }
                
        horasRepository.save(mapper.atualizaParaEntity(horaLancada, data));
    }
}
