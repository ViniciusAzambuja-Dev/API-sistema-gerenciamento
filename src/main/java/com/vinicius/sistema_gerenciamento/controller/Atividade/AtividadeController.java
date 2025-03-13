package com.vinicius.sistema_gerenciamento.controller.Atividade;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Atividade.AtividadeUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Error.ErrorResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Atividade.AtividadeService;

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
@RequestMapping("/api/atividades")
@Tag(name = "Atividade", description = "Controlador para salvar, listar, deletar e atualizar dados da Atividade")
public class AtividadeController {

    private final AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) { 
        this.atividadeService = atividadeService;
    }
    
    @PostMapping("/registrar")
    @Operation(summary = "Realiza cadastro de atividade", description = "Método para salvar dados da atividade")
    @ApiResponse(responseCode = "201", description = "Atividade cadastrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Projeto ou usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> registrar(@RequestBody @Valid AtividadeRequestDTO data) {
        atividadeService.registrarAtividade(data);

       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista dados das atividades", description = "Método para listar todas as atividades ativas")
    @ApiResponse(responseCode = "200", description = "Atividades listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = AtividadeResponseDTO.class)))
    )
    public ResponseEntity<List<AtividadeResponseDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarAtividades());
    }

    @GetMapping("/listar/usuario/{id}")
    @Operation(summary = "Lista atividades de um usuário", description = "Método para listar atividades através do Id de um usuário")
    @ApiResponse(responseCode = "200", description = "Atividades listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = AtividadeResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<AtividadeResponseDTO>> listarAtividadesPorUsuario(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarPorUsuario(id));
    }

    @GetMapping("/listar/projeto/{id}")
    @Operation(summary = "Lista atividades de um projeto", description = "Método para listar atividades através do Id de um projeto")
    @ApiResponse(responseCode = "200", description = "Atividades listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = AtividadeResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<AtividadeResponseDTO>> listarAtividadesPorProjeto(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(atividadeService.listarPorProjeto(id));
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza dados da atividade", description = "Método para atualizar dados de uma atividade")
    @ApiResponse(responseCode = "204", description = "Atividade atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Atividade, projeto ou usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AtividadeUpdateDTO data) {
        atividadeService.atualizarAtividades(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta dados da atividade", description = "Método deletar dados de uma atividade")
    @ApiResponse(responseCode = "204", description = "Atividade deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        atividadeService.softDeleteAtividade(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
