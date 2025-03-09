package com.vinicius.sistema_gerenciamento.controller.Relatorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.RelatorioAtividadeDTO;
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

    @GetMapping("/atividades/{id}")
    public ResponseEntity<RelatorioAtividadeDTO> filtrarAtividades(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarAtividades(id));
    }

    @GetMapping("/periodo/projetos")
    public ResponseEntity<List<ProjetoResponseDTO>> filtrarProjPorPeriodo(@RequestParam LocalDate periodoInicial, @RequestParam LocalDate periodoFinal) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarProjPorPeriodo(periodoInicial, periodoFinal));
    }

    @GetMapping("/periodo/atividades")
    public ResponseEntity<List<AtividadeResponseDTO>> filtrarAtivPorPeriodo(@RequestParam LocalDate periodoInicial, @RequestParam LocalDate periodoFinal) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarAtivPorPeriodo(periodoInicial, periodoFinal));
    }

    @GetMapping("/periodo/horas")
    public ResponseEntity<List<HorasResponseDTO>> filtrarHorasPorPeriodo(
        @RequestParam LocalDate periodoInicial,
        @RequestParam LocalDate periodoFinal, 
        @RequestParam(required = false) Integer usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarHorasPorPeriodo(periodoInicial, periodoFinal, usuarioId));
    }
}
