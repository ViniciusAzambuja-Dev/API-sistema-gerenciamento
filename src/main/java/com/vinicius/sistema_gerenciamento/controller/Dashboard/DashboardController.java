package com.vinicius.sistema_gerenciamento.controller.Dashboard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardAdminDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardGeneralDTO;
import com.vinicius.sistema_gerenciamento.service.Dashboard.DashboardService;

import jakarta.validation.constraints.Positive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Validated
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
  
    @GetMapping("/dados/admin")
    public ResponseEntity<DashboardAdminDTO> buscarMetricasAdmin() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.buscarMetricasAdmin());
    }

    @GetMapping("/dados/gerais/{id}")
    public ResponseEntity<DashboardGeneralDTO> buscarMetricasGerais(@PathVariable @Positive int id) {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.buscarMetricasGerais(id));
    }
}
