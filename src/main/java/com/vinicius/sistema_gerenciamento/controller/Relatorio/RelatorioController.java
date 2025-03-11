package com.vinicius.sistema_gerenciamento.controller.Relatorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Error.ErrorResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.RelatorioAtividadeDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.RelatorioProjetoDTO;
import com.vinicius.sistema_gerenciamento.service.Relatorio.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Validated
@RestController
@RequestMapping("api/relatorio")
@Tag(name = "Relatório", description = "Controlador para listar dados do relatório")
public class RelatorioController {

    private final RelatorioService relatorioService;
    
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/projetos/{id}")
    @Operation(summary = "Filtra um projeto", description = "Método para listar detalhes um projeto")
    @ApiResponse(responseCode = "200", description = "Detalhes do projeto listados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelatorioProjetoDTO.class)))
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<RelatorioProjetoDTO> filtrarProjetos(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarProjetos(id));
    }

    @GetMapping("/atividades/{id}")
    @Operation(summary = "Filtra uma atividade", description = "Método para listar detalhes uma atividade")
    @ApiResponse(responseCode = "200", description = "Detalhes da atividade listados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RelatorioAtividadeDTO.class)))
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<RelatorioAtividadeDTO> filtrarAtividades(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarAtividades(id));
    }

    @GetMapping("/periodo/projetos")
    @Operation(summary = "Filtra projeto(s) por período", description = "Método para listar projeto(s) por período")
    @ApiResponse(responseCode = "200", description = "Projeto(s) listado(s) com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ProjetoResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Datas incorretas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<ProjetoResponseDTO>> filtrarProjPorPeriodo(
        @RequestParam @NotNull LocalDate periodoInicial, 
        @RequestParam @NotNull LocalDate periodoFinal) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarProjPorPeriodo(periodoInicial, periodoFinal));
    }

    @GetMapping("/periodo/atividades")
    @Operation(summary = "Filtra atividade(s) por período", description = "Método para listar atividade(s) por período")
    @ApiResponse(responseCode = "200", description = "Atividade(s) listada(s) com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = AtividadeResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Datas incorretas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<AtividadeResponseDTO>> filtrarAtivPorPeriodo(
        @RequestParam @NotNull LocalDate periodoInicial, 
        @RequestParam @NotNull LocalDate periodoFinal) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarAtivPorPeriodo(periodoInicial, periodoFinal));
    }

    @GetMapping("/periodo/horas")
    @Operation(summary = "Filtra hora(s) por período", description = "Método para listar hora(s) por período com opcional de pesquisar por usuário")
    @ApiResponse(responseCode = "200", description = "Hora(s) listada(s) com sucesso", content = @Content(
        mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = HorasResponseDTO.class)))
    )
    @ApiResponse(responseCode = "400", description = "Datas incorretas ou Id negativo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<List<HorasResponseDTO>> filtrarHorasPorPeriodo(
        @RequestParam @NotNull LocalDate periodoInicial,
        @RequestParam @NotNull LocalDate periodoFinal, 
        @RequestParam(required = false) @Positive Integer usuarioId) {
        return ResponseEntity.status(HttpStatus.OK).body(relatorioService.filtrarHorasPorPeriodo(periodoInicial, periodoFinal, usuarioId));
    }
}
