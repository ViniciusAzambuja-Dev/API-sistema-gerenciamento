package com.vinicius.sistema_gerenciamento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.service.HorasService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

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
}
