package com.vinicius.sistema_gerenciamento.service.UsuarioAtividade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.exception.RecordNotFoundException;
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

    public void registrar(Atividade atividade, List<Integer> integrantesIds) {
        for(int integranteId : integrantesIds) {
            Usuario usuario = usuarioRepository.findById(integranteId)
                .orElseThrow(() -> new RecordNotFoundException(integranteId));
            
            UsuarioAtividade usuarioAtividade = new UsuarioAtividade(usuario, atividade);
            repository.save(usuarioAtividade);
        }
    }
}