package com.vinicius.sistema_gerenciamento.controller.Usuario;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Usuario.LoginRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Usuario.UsuarioUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Error.ErrorResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.LoginResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Usuario.UsuarioResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Usuario.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuario", description = "Controlador para salvar, listar, deletar e atualizar dados do Usuário")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }   

    @PostMapping("/auth/login")
    @Operation(summary = "Realiza login de Usuário", description = "Método para realizar login do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var token = usuarioService.realizarLogin(data);

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    @PostMapping("/registrar")
    @Operation(summary = "Realiza cadastro de Usuário", description = "Método para salvar dados do usuário")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    @ApiResponse(responseCode = "401", description = "Email em uso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> registrar(@RequestBody @Valid UsuarioRequestDTO data) {
        usuarioService.registrarUsuario(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista dados dos usuários", description = "Método para listar todos os usuários ativos")
    @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    )
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    @GetMapping("/listar/integrantes/{id}")
    @Operation(summary = "Lista integrantes de um Projeto", description = "Método para listar integrantes através do Id de um Projeto")
    @ApiResponse(responseCode = "200", description = "Integrantes listados com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<UsuarioResponseDTO>> listarIntegrantesDoProjeto(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarIntegrantesDoProjeto(id));
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza dados do usuário", description = "Método para atualizar dados de um usuário")
    @ApiResponse(responseCode = "204", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Email em uso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos inválidos!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> atualizar(@RequestBody @Valid UsuarioUpdateDTO data) {
        usuarioService.atualizaUsuario(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta dados do usuário", description = "Método deletar dados de um usuário")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        usuarioService.softDeleteUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}