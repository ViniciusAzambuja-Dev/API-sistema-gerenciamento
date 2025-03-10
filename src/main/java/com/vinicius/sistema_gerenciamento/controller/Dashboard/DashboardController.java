package com.vinicius.sistema_gerenciamento.controller.Dashboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardAdminDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardGeneralDTO;
import com.vinicius.sistema_gerenciamento.service.Dashboard.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Validated
@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "Controlador para listar dados do Dashboard")
public class DashboardController {
    
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
  
    @GetMapping("/dados/admin")
    @Operation(summary = "Lista métricas do administrador", description = "Método para listar métricas do administrador")
    @ApiResponse(responseCode = "200", description = "Métricas do administrador listadas com sucesso")
    public ResponseEntity<DashboardAdminDTO> buscarMetricasAdmin() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.buscarMetricasAdmin());
    }

    @GetMapping("/dados/gerais/{id}")
    @Operation(summary = "Lista métricas gerais", description = "Método para listar métricas gerais através do id do usuário logado")
    @ApiResponse(responseCode = "200", description = "Métricas gerais do usuário logado listadas com sucesso")
    @ApiResponse(responseCode = "400", description = "Id deve ser positivo")
    public ResponseEntity<DashboardGeneralDTO> buscarMetricasGerais(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.buscarMetricasGerais(id));
    }
}
