package com.vinicius.sistema_gerenciamento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.service.HorasService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable int id, @RequestBody HorasRequestDTO data) {
        horasService.atualizarHoras(id, data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        horasService.deletarHoras(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
