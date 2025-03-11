package com.vinicius.sistema_gerenciamento.service.Atividade;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.AtividadeMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.AtividadeDetalhesDTO;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.SoftDelete.SoftDeleteService;
import com.vinicius.sistema_gerenciamento.service.UsuarioAtividade.UsuarioAtividadeService;

@Service
public class AtividadeService {
    private final AtividadeRepository atividadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final UsuarioAtividadeService usuarioAtividadeService;
    private final AtividadeMapper mapper;
    private final SoftDeleteService softDeleteService;

    public AtividadeService(
        UsuarioAtividadeService usuarioAtividadeService,
        AtividadeRepository atividadeRepository, 
        AtividadeMapper mapper, 
        UsuarioRepository usuarioRepository, 
        ProjetoRepository projetoRepository, 
        SoftDeleteService softDeleteService
    ) {
        this.usuarioAtividadeService = usuarioAtividadeService;
        this.atividadeRepository = atividadeRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.softDeleteService = softDeleteService;
        this.mapper = mapper;
    }

    /**
     * Registra uma nova atividade no sistema.
     *
     * @param data DTO contendo os dados da atividade, o ID do usuário responsável e o ID do projeto.
     * @throws RecordNotFoundException Se o usuário ou o projeto não forem encontrados.
     */
    public void registrarAtividade(AtividadeRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

        Projeto projeto = projetoRepository.findById(data.projetoId())
            .orElseThrow(() -> new RecordNotFoundException(data.projetoId()));

        Atividade atividade = atividadeRepository.save(mapper.paraEntity(data, usuario, projeto));  
        usuarioAtividadeService.registrar(atividade, data.integrantesIds());
    }

    /**
     * Retorna uma lista de todas as atividades ativas.
     *
     * @return Lista de DTOs contendo os dados das atividades ativas.
     */
    public List<AtividadeResponseDTO> listarAtividades() {
        return atividadeRepository.findAllAtivado()
            .stream()
            .map(atividade -> mapper.paraDTO(atividade))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de atividades associadas a um usuário.
     *
     * @param id ID do usuário.
     * @return Lista de DTOs contendo os dados das atividades do usuário.
     */
    public List<AtividadeResponseDTO> listarPorUsuario(int id) {
        return atividadeRepository.findByUsuarioId(id)
            .stream()
            .map(atividade -> mapper.paraDTO(atividade))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de atividades associadas a um projeto.
     *
     * @param id ID do projeto.
     * @return Lista de DTOs contendo os dados das atividades do projeto.
     */
    public List<AtividadeResponseDTO> listarPorProjeto(int id) {
        return atividadeRepository.findByProjetoId(id)
            .stream()
            .map(atividade -> mapper.paraDTO(atividade))
            .collect(Collectors.toList());
    }

    /**
     * Retorna os detalhes de uma atividade específica(Relatório).
     *
     * @param id ID da atividade.
     * @return DTO contendo os detalhes da atividade.
     * @throws RecordNotFoundException Se a atividade não for encontrada.
     */
    public AtividadeDetalhesDTO listarDetalhes(int id) {
        return atividadeRepository.findAtividadeDetalhes(id)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    /**
     * Retorna uma lista de atividades ativas dentro de um período específico
     *
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @return Lista de DTOs contendo os dados das atividades no período.
     */
    public List<AtividadeResponseDTO> listarPorPeriodo(LocalDate periodoInicial, LocalDate periodoFinal) {
        return atividadeRepository.findByPeriodo(periodoInicial, periodoFinal)
            .stream()
            .map(atividade -> mapper.paraDTO(atividade))
            .collect(Collectors.toList());
    }

    /**
     * Atualiza os dados de uma atividade existente.
     *
     * @param data DTO contendo os novos dados da atividade.
     * @throws RecordNotFoundException Se a atividade, o usuário ou o projeto não forem encontrados.
     */
    public void atualizarAtividades(AtividadeUpdateDTO data) {
        Atividade atividade = atividadeRepository.findById(data.atividadeId())
            .orElseThrow(() -> new RecordNotFoundException(data.atividadeId()));

        if (atividade.getUsuario_responsavel().getId() != data.usuarioId()) {
            Usuario usuario = usuarioRepository.findById(data.usuarioId())
                .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));  

            atividade.setUsuario_responsavel(usuario);
        }
        if (atividade.getProjeto().getId() != data.projetoId()) {
            Projeto projeto = projetoRepository.findById(data.projetoId())
                .orElseThrow(() -> new RecordNotFoundException(data.projetoId()));
            
            atividade.setProjeto(projeto);
        }
        atividadeRepository.save(mapper.atualizaParaEntity(atividade, data));
    }

    /**
     * Realiza a exclusão lógica de uma atividade.
     *
     * @param id ID da atividade a ser desativada.
     * @throws RecordNotFoundException Se a atividade não for encontrada ou já estiver desativada.
     */
    public void softDeleteAtividade(int id) {
        if (!atividadeRepository.existsByIdAndAtivado(id)) {
            throw new RecordNotFoundException(id);
        }
        softDeleteService.softDeleteAtividade(id);
    }
}
