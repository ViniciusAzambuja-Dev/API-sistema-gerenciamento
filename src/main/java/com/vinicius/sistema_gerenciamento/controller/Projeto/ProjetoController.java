package com.vinicius.sistema_gerenciamento.controller.Projeto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Projeto.ProjetoUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Error.ErrorResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;

import com.vinicius.sistema_gerenciamento.service.Projeto.ProjetoService;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping("/api/projetos")
@Tag(name = "Projeto", description = "Controlador para salvar, listar, deletar e atualizar dados do Projeto")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
    }
    
    @PostMapping("/registrar")
    @Operation(summary = "Realiza cadastro de projeto", description = "Método para salvar dados do projeto")
    @ApiResponse(responseCode = "201", description = "Projeto cadastrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> registrar(@RequestBody @Valid ProjetoRequestDTO data) {
        projetoService.registrarProjeto(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista dados dos projetos", description = "Método para listar todos os projetos ativos")
    @ApiResponse(responseCode = "200", description = "Projetos listados com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ProjetoResponseDTO.class)))
    )
    public ResponseEntity<List<ProjetoResponseDTO>> listar() {
       return ResponseEntity.status(HttpStatus.OK).body(projetoService.listarProjetos());
    }

    @GetMapping("/listar/usuario/{id}")
    @Operation(summary = "Lista projetos de um usuário", description = "Método para listar projetos através do Id de um usuário")
    @ApiResponse(responseCode = "200", description = "Projetos listados com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ProjetoResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<ProjetoResponseDTO>> listarProjetosPorUsuario(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(projetoService.listarPorUsuario(id));
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza dados do projeto", description = "Método para atualizar dados de um projeto")
    @ApiResponse(responseCode = "204", description = "Projeto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Projeto ou usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> atualizar(@RequestBody @Valid ProjetoUpdateDTO data) {
        projetoService.atualizarProjeto(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta dados do projeto", description = "Método deletar dados de um projeto")
    @ApiResponse(responseCode = "204", description = "Projeto deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Projeto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
     public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        projetoService.softDeleteProjeto(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }    
}
