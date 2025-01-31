package com.vinicius.sistema_gerenciamento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.service.ProjetoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    @DeleteMapping("/deletar/{id}")
     public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        projetoService.deletarProjeto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
