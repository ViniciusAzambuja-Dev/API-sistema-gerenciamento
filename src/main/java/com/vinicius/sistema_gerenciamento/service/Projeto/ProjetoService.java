package com.vinicius.sistema_gerenciamento.service.Projeto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.mapper.ProjetoMapper;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.ProjetoDetalhesDTO;
import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.service.SoftDelete.SoftDeleteService;
import com.vinicius.sistema_gerenciamento.service.UsuarioProjeto.UsuarioProjetoService;


@Service
public class ProjetoService {
    
    private final UsuarioProjetoService usuarioProjetoService;
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProjetoMapper projetoMapper;
    private final SoftDeleteService softDeleteService;

    public ProjetoService(
        ProjetoRepository projetoRepository,
        UsuarioRepository usuarioRepository,
        ProjetoMapper projetoMapper,
        UsuarioProjetoService usuarioProjetoService,
        SoftDeleteService softDeleteService
    ) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.projetoMapper = projetoMapper;
        this.usuarioProjetoService  = usuarioProjetoService;
        this.softDeleteService = softDeleteService;
    }

    /**
     * Registra um novo projeto no sistema.
     *
     * @param data DTO contendo os dados do projeto e o ID do usuário responsável.
     * @throws RecordNotFoundException Se o usuário responsável não for encontrado.
     */
    public void registrarProjeto(ProjetoRequestDTO data) {
        Usuario usuario = usuarioRepository.findById(data.usuarioId())
            .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));

        Projeto projeto = projetoRepository.save(projetoMapper.paraEntity(data, usuario));
        usuarioProjetoService.registrar(projeto, data.integrantesIds());
    }

    /**
     * Retorna uma lista de todos os projetos ativos.
     *
     * @return Lista de DTOs contendo os dados dos projetos ativos.
     */
    public List<ProjetoResponseDTO> listarProjetos() {
        return projetoRepository.findAllAtivado()
            .stream()
            .map(projeto -> projetoMapper.paraDTO(projeto))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de projetos associados a um usuário.
     *
     * @param id ID do usuário.
     * @return Lista de DTOs contendo os dados dos projetos do usuário.
     */
    public List<ProjetoResponseDTO> listarPorUsuario(int id) {
        return projetoRepository.findByUsuarioId(id)
            .stream()
            .map(projeto -> projetoMapper.paraDTO(projeto))
            .collect(Collectors.toList());
    }

    /**
     * Retorna uma lista de projetos ativos dentro de um período específico.
     *
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @return Lista de DTOs contendo os dados dos projetos no período.
     */
    public List<ProjetoResponseDTO> listarPorPeriodo(LocalDate periodoInicial, LocalDate periodoFinal) {
        return projetoRepository.findByPeriodo(periodoInicial, periodoFinal)
            .stream()
            .map(projeto -> projetoMapper.paraDTO(projeto))
            .collect(Collectors.toList());
    }

    /**
     * Retorna os detalhes de um projeto específico(Relatório).
     *
     * @param id ID do projeto.
     * @return DTO contendo os detalhes do projeto.
     * @throws RecordNotFoundException Se o projeto não for encontrado.
     */
    public ProjetoDetalhesDTO listarDetalhes(int id) {
        return projetoRepository.findProjetoDetalhes(id)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

     /**
     * Atualiza os dados de um projeto existente.
     *
     * @param data DTO contendo os novos dados do projeto.
     * @throws RecordNotFoundException Se o projeto ou o novo usuário responsável não forem encontrados.
     */
    public void atualizarProjeto(ProjetoUpdateDTO data) {
        Projeto projeto = projetoRepository.findById(data.projetoId())
            .orElseThrow(() -> new RecordNotFoundException(data.projetoId()));

        if (projeto.getUsuario_responsavel().getId() != data.usuarioId()) {
            Usuario usuario = usuarioRepository.findById(data.usuarioId())
                .orElseThrow(() -> new RecordNotFoundException(data.usuarioId()));
            projeto.setUsuario_responsavel(usuario);
        }

        projetoRepository.save(projetoMapper.atualizaParaEntity(projeto, data));
    }

    /**
     * Realiza a exclusão lógica de um projeto.
     *
     * @param id ID do projeto a ser desativado.
     * @throws RecordNotFoundException Se o projeto não for encontrado ou já estiver desativado.
     */
    public void softDeleteProjeto(int id) {
        if (!projetoRepository.existsByIdAndAtivado(id)) {
            throw new RecordNotFoundException(id);
        }
        softDeleteService.softDeleteProjeto(id);
    }
}
