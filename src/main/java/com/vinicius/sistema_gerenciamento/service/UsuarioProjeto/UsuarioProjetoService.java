package com.vinicius.sistema_gerenciamento.service.UsuarioProjeto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.model.UsuarioProjeto.UsuarioProjeto;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioProjeto.UsuarioProjetoRepository;

@Service
public class UsuarioProjetoService {
    private final UsuarioProjetoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioProjetoService(UsuarioProjetoRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public void registrar(Projeto projeto, List<Integer> integrantesIds) { 
        List<Usuario> integrantes = usuarioRepository.findAllById(integrantesIds);

        List<UsuarioProjeto> associacoes = integrantes.stream().map(integrante ->
            new UsuarioProjeto(integrante, projeto)
        ).collect(Collectors.toList());

        repository.saveAll(associacoes);
    }
}