package com.vinicius.sistema_gerenciamento.service.SoftDelete;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Horas.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class SoftDeleteService {
    
    private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final AtividadeRepository atividadeRepository;
    private final HorasRepository horasRepository;

    public SoftDeleteService(
        UsuarioRepository usuarioRepository,
        ProjetoRepository projetoRepository,
        AtividadeRepository atividadeRepository,
        HorasRepository horasRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.projetoRepository = projetoRepository;
        this.atividadeRepository = atividadeRepository;
        this.horasRepository = horasRepository;
    }

    /**
     * Realiza a exclusão lógica de um usuário e todas as suas dependências (horas trabalhadas).
     *
     * @param id ID do usuário a ser desativado.
     */
    @Transactional
    public void softDeleteUsuario(int id) {
        horasRepository.deleteByUsuarioId(id);
        usuarioRepository.deleteUsuarioById(id);
    }

    /**
     * Realiza a exclusão lógica de um projeto e todas as suas dependências (atividades e horas trabalhadas).
     *
     * @param id ID do projeto a ser desativado.
     */
    @Transactional
    public void softDeleteProjeto(int id) {
        horasRepository.deleteByProjetoId(id);
        atividadeRepository.deleteByProjetoId(id);
        projetoRepository.deleteProjetoById(id);
    }

    /**
     * Realiza a exclusão lógica de uma atividade e todas as suas dependências (horas trabalhadas).
     *
     * @param atividadeId ID da atividade a ser desativada.
     */
    @Transactional
    public void softDeleteAtividade(int atividadeId) {
        horasRepository.deleteByAtividadeId(atividadeId);
        atividadeRepository.deleteAtividadeById(atividadeId);
    }

    /**
     * Realiza a exclusão lógica de um lançamento de horas.
     *
     * @param horaId ID do lançamento de horas a ser desativado.
     */
    @Transactional
    public void softDeleteHora(int horaId) {
        horasRepository.deleteHoraById(horaId);
    }
}
