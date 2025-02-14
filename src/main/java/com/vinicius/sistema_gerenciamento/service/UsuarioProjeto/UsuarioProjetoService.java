package com.vinicius.sistema_gerenciamento.service.UsuarioProjeto;

import java.util.List;

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

    public void registrar(Projeto projeto, List<Integer> usuariosIds) {
        for(int id : usuariosIds) {
            Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

            UsuarioProjeto usuarioProjeto = new UsuarioProjeto();
            usuarioProjeto.setProjeto(projeto);
            usuarioProjeto.setUsuario(usuario);

            repository.save(usuarioProjeto);
        }
    }
}