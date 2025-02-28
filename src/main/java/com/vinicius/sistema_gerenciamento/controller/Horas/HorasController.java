package com.vinicius.sistema_gerenciamento.controller.Horas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Horas.HorasService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
@RestController
@RequestMapping("/api/horas")
public class HorasController {

    private final HorasService horasService;

    public HorasController(HorasService horasService) {
        this.horasService = horasService;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid HorasRequestDTO data) {
        horasService.registrarHoras(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();   
    }

    @GetMapping("/listar")
    public ResponseEntity<List<HorasResponseDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarHoras());
    }

    @GetMapping("/listar/atividade/{id}")
    public ResponseEntity<List<HorasResponseDTO>> listarHorasPorAtividade(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarPorAtividade(id));
    }

    @GetMapping("/listar/usuario/{id}")
    public ResponseEntity<List<HorasResponseDTO>> listarHorasPorUsuario(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarPorUsuario(id));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid HorasUpdateDTO data) {
        horasService.atualizarHoras(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        horasService.softDeleteHora(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
