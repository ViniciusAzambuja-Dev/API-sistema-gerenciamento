package com.vinicius.sistema_gerenciamento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.service.HorasService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    
}
