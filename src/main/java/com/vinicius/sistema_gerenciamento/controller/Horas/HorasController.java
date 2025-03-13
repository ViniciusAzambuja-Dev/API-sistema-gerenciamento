package com.vinicius.sistema_gerenciamento.controller.Horas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasRequestDTO;
import com.vinicius.sistema_gerenciamento.dto.request.Horas.HorasUpdateDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Error.ErrorResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.service.Horas.HorasService;

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
@RequestMapping("/api/horas")
@Tag(name = "Lançamento de Horas", description = "Controlador para salvar, listar, deletar e atualizar dados de Horas trabalhadas")
public class HorasController {

    private final HorasService horasService;

    public HorasController(HorasService horasService) {
        this.horasService = horasService;
    }
    
    @PostMapping("/registrar")
    @Operation(summary = "Realiza lançamento de horas", description = "Método para lançar horas")
    @ApiResponse(responseCode = "201", description = "Hora lançada com sucesso")
    @ApiResponse(responseCode = "404", description = "Atividade ou usuário não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> registrar(@RequestBody @Valid HorasRequestDTO data) {
        horasService.registrarHoras(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();   
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista horas lançadas", description = "Método para listar todas as horas ativas")
    @ApiResponse(responseCode = "200", description = "Horas lançadas listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = HorasResponseDTO.class)))
    )
    public ResponseEntity<List<HorasResponseDTO>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarHoras());
    }

    @GetMapping("/listar/atividade/{id}")
    @Operation(summary = "Lista horas lançadas de uma atividade", description = "Método para listar horas através do Id de uma atividade")
    @ApiResponse(responseCode = "200", description = "Horas lançadas listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = HorasResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<HorasResponseDTO>> listarHorasPorAtividade(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarPorAtividade(id));
    }

    @GetMapping("/listar/usuario/{id}")
    @Operation(summary = "Lista horas lançadas de um usuário", description = "Método para listar horas através do Id de um usuário")
    @ApiResponse(responseCode = "200", description = "Horas lançadas listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = HorasResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<HorasResponseDTO>> listarHorasPorUsuario(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarPorUsuario(id));
    }

    @GetMapping("/listar/mes/usuario/{id}")
    @Operation(summary = "Lista horas lançadas de um usuário no mês", description = "Método para listar horas de um usuário no mês")
    @ApiResponse(responseCode = "200", description = "Horas lançadas listadas com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = HorasResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<HorasResponseDTO>> listarPorMesUsuario(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(horasService.listarPorMesUsuario(id));
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza dados de horas", description = "Método para atualizar horas lançadas")
    @ApiResponse(responseCode = "204", description = "Hora atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Hora ou atividade não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Campos incorretos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> atualizar(@RequestBody @Valid HorasUpdateDTO data) {
        horasService.atualizarHoras(data);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta dados de horas", description = "Método deletar um lançamento de hora")
    @ApiResponse(responseCode = "204", description = "Hora lançada deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Hora lançada não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<Void> deletar(@PathVariable @Positive int id) {
        horasService.softDeleteHora(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
