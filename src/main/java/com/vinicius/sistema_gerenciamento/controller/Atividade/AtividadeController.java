package com.vinicius.sistema_gerenciamento.controller.Atividade;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Atividade.AtividadeService;

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
@RequestMapping("/api/atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) { 
        this.atividadeService = atividadeService;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid AtividadeRequestDTO data) {
        atividadeService.registrarAtividade(data);

       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<AtividadeResponseDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarAtividades());
    }

    @GetMapping("/listar/usuario/{id}")
    public ResponseEntity<List<AtividadeResponseDTO>> listarAtividadesPorUsuario(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarPorUsuario(id));
    }

    @GetMapping("/listar/projeto/{id}")
    public ResponseEntity<List<AtividadeResponseDTO>> listarAtividadesPorProjeto(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarPorProjeto(id));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtividadeUpdateDTO data) {
        atividadeService.atualizarAtividades(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        atividadeService.softDeleteAtividade(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/horas")
    public ResponseEntity<List<HorasResponseDTO>> listarHorasPorAtividade(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarHoras(id));
    }
}
