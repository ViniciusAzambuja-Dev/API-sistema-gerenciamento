package com.vinicius.sistema_gerenciamento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.service.AtividadeService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) { 
        this.atividadeService = atividadeService;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid AtividadeRequestDTO data) {
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
