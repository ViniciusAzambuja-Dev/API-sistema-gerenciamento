package com.vinicius.sistema_gerenciamento.service.Relatorio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.response.Atividade.AtividadeResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Horas.HorasResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Projeto.ProjetoResponseDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.AtividadeDetalhesDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Atividade.RelatorioAtividadeDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.ProjetoDetalhesDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Relatorio.Projeto.RelatorioProjetoDTO;
import com.vinicius.sistema_gerenciamento.service.Atividade.AtividadeService;
import com.vinicius.sistema_gerenciamento.service.Horas.HorasService;
import com.vinicius.sistema_gerenciamento.service.Projeto.ProjetoService;

@Service
public class RelatorioService {
    private final ProjetoService projetoService;
    private final AtividadeService atividadeService;
    private final HorasService horasService;

    public RelatorioService(
      ProjetoService projetoService, 
      AtividadeService atividadeService,
      HorasService horasService) {
       this.projetoService = projetoService;
       this.atividadeService = atividadeService;
       this.horasService = horasService;
    }

    /**
     * Gera um relatório detalhado de um projeto, incluindo atividades associadas e gráfico.
     *
     * @param id ID do projeto.
     * @return DTO contendo o relatório do projeto.
     */
    public RelatorioProjetoDTO filtrarProjetos(int id) {
      List<AtividadeResponseDTO> atividades = atividadeService.listarPorProjeto(id);
      ProjetoDetalhesDTO detalhes = projetoService.listarDetalhes(id);
      List<GraficoBarrasDTO> dadosGraficoBarras = horasService.somaHorasPorAtividade(id);
      
      return new RelatorioProjetoDTO(atividades, dadosGraficoBarras, detalhes);
    }

    /**
     * Gera um relatório detalhado de uma atividade, incluindo horas trabalhadas e métricas.
     *
     * @param id ID da atividade.
     * @return DTO contendo o relatório da atividade.
     */
    public RelatorioAtividadeDTO filtrarAtividades(int id) {
      List<HorasResponseDTO> horas = horasService.listarPorAtividade(id);
      AtividadeDetalhesDTO detalhes = atividadeService.listarDetalhes(id);
      
      return new RelatorioAtividadeDTO(horas, detalhes);
    }

    /**
     * Filtra projetos ativos dentro de um período específico.
     *
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @return Lista de DTOs contendo os projetos no período.
     */
    public List<ProjetoResponseDTO> filtrarProjPorPeriodo(LocalDate periodoInicial, LocalDate periodoFinal) {
      return projetoService.listarPorPeriodo(periodoInicial, periodoFinal);
    }

    /**
     * Filtra atividades ativas dentro de um período específico.
     *
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @return Lista de DTOs contendo as atividades no período.
     */
    public List<AtividadeResponseDTO> filtrarAtivPorPeriodo(LocalDate periodoInicial, LocalDate periodoFinal) {
      return atividadeService.listarPorPeriodo(periodoInicial, periodoFinal);
    }

    /**
     * Filtra horas trabalhadas dentro de um período específico, opcionalmente filtradas por usuário.
     *
     * @param periodoInicial Data inicial do período.
     * @param periodoFinal Data final do período.
     * @param usuarioId ID do usuário (opcional).
     * @return Lista de DTOs contendo as horas trabalhadas no período.
     */
    public List<HorasResponseDTO> filtrarHorasPorPeriodo(LocalDate periodoInicial, LocalDate periodoFinal, Integer usuarioId) {
      LocalDateTime dataInicial = periodoInicial.atStartOfDay();
      LocalDateTime dataFinal = periodoFinal.atTime(LocalTime.MAX);

      return horasService.listarPorPeriodo(dataInicial, dataFinal, usuarioId);
    }
}
