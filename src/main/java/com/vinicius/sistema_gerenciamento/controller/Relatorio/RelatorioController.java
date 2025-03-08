package com.vinicius.sistema_gerenciamento.controller.Relatorio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.RelatorioProjetoDTO;
import com.vinicius.sistema_gerenciamento.service.Relatorio.RelatorioService;

import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Validated
@RestController
@RequestMapping("api/relatorio")
public class RelatorioController {

    private final RelatorioService relatorioService;
    
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/projetos/{id}")
    public ResponseEntity<RelatorioProjetoDTO> filtrarProjetos(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarProjetos(id));
    }
}
