package com.vinicius.sistema_gerenciamento.controller.Projeto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Projeto.ProjetoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid ProjetoRequestDTO data) {
        projetoService.registrarProjeto(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProjetoResponseDTO>> listar() {
       return ResponseEntity.status(HttpStatus.OK).body(projetoService.listarProjetos());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid ProjetoUpdateDTO data) {
        projetoService.atualizarProjeto(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
     public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        projetoService.softDeleteProjeto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/atividades")
    public ResponseEntity<List<AtividadeResponseDTO>> listarAtividadesPorProjeto(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(projetoService.listarAtividades(id));
    }
    
}
