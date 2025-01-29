package com.vinicius.sistema_gerenciamento.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.LoginRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }   

    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDTO data) {
        usuarioService.realizarLogin(data);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid UsuarioRequestDTO data) {
        boolean autorizado = usuarioService.registrarUsuario(data);

        if (!autorizado) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email em uso!");
        }
       
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}