package com.vinicius.sistema_gerenciamento.controller.Usuario;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.infra.seguranca.dto.LoginRequestDTO;
import com.vinicius.sistema_gerenciamento.infra.seguranca.dto.LoginResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Usuario.UsuarioService;

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
@RequestMapping("api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }   

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var token = usuarioService.realizarLogin(data);

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid UsuarioRequestDTO data) {
        usuarioService.registrarUsuario(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid UsuarioUpdateDTO data) {
        usuarioService.atualizaUsuario(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        usuarioService.softDeleteUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}