package com.vinicius.sistema_gerenciamento.service.UsuarioAtividade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.model.UsuarioAtividade.UsuarioAtividade;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioAtividade.UsuarioAtividadeRepository;

@Service
public class UsuarioAtividadeService {
    private final UsuarioAtividadeRepository repository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioAtividadeService(UsuarioAtividadeRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra a associação entre uma atividade e uma lista de usuários (integrantes).
     *
     * @param atividade Atividade a ser associada.
     * @param integrantesIds Lista de IDs dos usuários (integrantes) a serem associados à atividade.
     */
    public void registrar(Atividade atividade, List<Integer> integrantesIds) {
        List<Usuario> integrantes = usuarioRepository.findAllById(integrantesIds);

        List<UsuarioAtividade> associacoes = integrantes.stream().map(integrante ->
            new UsuarioAtividade(integrante, atividade)
        ).collect(Collectors.toList());

        repository.saveAll(associacoes);
    }
}