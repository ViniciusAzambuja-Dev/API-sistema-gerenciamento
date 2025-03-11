package com.vinicius.sistema_gerenciamento.service.Horas;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.HorasMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;
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

    /**
     * Registra um novo lançamento de horas no sistema.
     *
     * @param data DTO contendo os dados do lançamento de horas.
     * @throws RecordNotFoundException Se o usuário ou a atividade não forem encontrados.
     */
    public void registrarHoras(HorasRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

        Atividade atividade = atividadeRepository.findById(data.atividadeId())
            .orElseThrow(() -> new RecordNotFoundException(data.atividadeId()));

        horasRepository.save(mapper.paraEntity(data, usuario, atividade));
    }

    /**
     * Retorna uma lista de todos os lançamentos de horas ativos.
     *
     * @return Lista de DTOs contendo os dados dos lançamentos de horas ativos.
     */
    public List<HorasResponseDTO> listarHoras() {
        return horasRepository.findAllAtivado()
            .stream()
            .map(horaLancada -> mapper.paraDTO(horaLancada))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de lançamentos de horas associados a uma atividade.
     *
     * @param id ID da atividade.
     * @return Lista de DTOs contendo os dados dos lançamentos de horas da atividade.
     */
    public List<HorasResponseDTO> listarPorAtividade(int id) {
        return horasRepository.findByAtividadeId(id)
            .stream()
            .map(hora -> mapper.paraDTO(hora))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de lançamentos de horas associados a um usuário.
     *
     * @param id ID do usuário.
     * @return Lista de DTOs contendo os dados dos lançamentos de horas do usuário.
     */
    public List<HorasResponseDTO> listarPorUsuario(int id) {
        return horasRepository.findByUsuarioId(id)
            .stream()
            .map(hora -> mapper.paraDTO(hora))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de lançamentos de horas de um usuário no mês atual.
     *
     * @param id ID do usuário.
     * @return Lista de DTOs contendo os dados dos lançamentos de horas do usuário no mês atual.
     */
    public List<HorasResponseDTO> listarPorMesUsuario(int id) {
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return horasRepository.findAllPorMesAndUsuario(mesAtual, id)
            .stream()
            .map(hora -> mapper.paraDTO(hora))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de lançamentos de horas dentro de um período específico, opcionalmente filtrados por usuário.
     *
     * @param dataInicial Data inicial do período.
     * @param dataFinal Data final do período.
     * @param usuarioId ID do usuário (opcional).
     * @return Lista de DTOs contendo os dados dos lançamentos de horas no período.
     */
    public List<HorasResponseDTO> listarPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, Integer usuarioId) {
        return horasRepository.findByPeriodo(dataInicial, dataFinal, usuarioId)
            .stream()
            .map(hora -> mapper.paraDTO(hora))
            .collect(Collectors.toList());
    }

     /**
     * Atualiza os dados de um lançamento de horas existente.
     *
     * @param data DTO contendo os novos dados do lançamento de horas.
     * @throws RecordNotFoundException Se o lançamento de horas ou a nova atividade não forem encontrados.
     */
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

    /**
     * Retorna a soma de horas lançadas por atividade de um projeto específico, encapsulada em um DTO.
     *
     * @param id ID do projeto.
     * @return Lista de DTOs contendo a soma de horas por atividade.
     */
    public List<GraficoBarrasDTO> somaHorasPorAtividade(int id) {
        return horasRepository.sumHorasPorAtividade(id);
    }
 
    /**
     * Realiza a exclusão lógica de um lançamento de horas.
     *
     * @param id ID do lançamento de horas a ser desativado.
     * @throws RecordNotFoundException Se o lançamento de horas não for encontrado ou já estiver desativado.
     */
    public void softDeleteHora(int id) {
        if (!horasRepository.existsByIdAndAtivado(id)) {
            throw new RecordNotFoundException(id);
        }
        softDeleteService.softDeleteHora(id);
    }
}