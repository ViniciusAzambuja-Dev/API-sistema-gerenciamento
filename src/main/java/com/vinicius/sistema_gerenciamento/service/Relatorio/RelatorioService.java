package com.vinicius.sistema_gerenciamento.service.Relatorio;

import java.time.LocalDate;
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

    public RelatorioProjetoDTO filtrarProjetos(int id) {
      List<AtividadeResponseDTO> atividades = atividadeService.listarPorProjeto(id);
      ProjetoDetalhesDTO detalhes = projetoService.listarDetalhes(id);
      List<GraficoBarrasDTO> dadosGraficoBarras = horasService.somaHorasPorAtividade(id);
      
      return new RelatorioProjetoDTO(atividades, dadosGraficoBarras, detalhes);
    }

    public RelatorioAtividadeDTO filtrarAtividades(int id) {
      List<HorasResponseDTO> horas = horasService.listarPorAtividade(id);
      AtividadeDetalhesDTO detalhes = atividadeService.listarDetalhes(id);
      
      return new RelatorioAtividadeDTO(horas, detalhes);
    }

    public List<ProjetoResponseDTO> filtrarProjPorPeriodo(LocalDate periodoInicial, LocalDate periodoFinal) {
      return projetoService.listarPorPeriodo(periodoInicial, periodoFinal);
    }
}
