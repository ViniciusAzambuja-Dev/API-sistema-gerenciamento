package com.vinicius.sistema_gerenciamento.service.Dashboard;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardAdminDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardGeneralDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoBarrasDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Grafico.GraficoDoughnutDTO;
import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Horas.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;

@Service
public class DashboardService {
    private final AtividadeRepository atividadeRepository;
    private final ProjetoRepository projetoRepository;
    private final HorasRepository horasRepository;
    private final UsuarioRepository usuarioRepository;

    public DashboardService(
        AtividadeRepository atividadeRepository,
        ProjetoRepository projetoRepository,
        HorasRepository horasRepository,
        UsuarioRepository usuarioRepository
    ) {
        this.atividadeRepository = atividadeRepository;
        this.projetoRepository = projetoRepository;
        this.horasRepository = horasRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Busca métricas para o dashboard de administradores,  
     * incluindo informações sobre projetos, atividades, usuários e horas trabalhadas.
     *
     * @return DTO contendo as métricas do dashboard de administradores.
     */
    public DashboardAdminDTO buscarMetricasAdmin() {
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int projetosConcluidos = projetoRepository.countByStatus("CONCLUIDO");
        int projetosPendentes = projetoRepository.countByStatus("EM_ANDAMENTO");
        int projetosPlanejados = projetoRepository.countByStatus("PLANEJADO");
        int projetosCancelados = projetoRepository.countByStatus("CANCELADO");

        int atividadesConcluidas = atividadeRepository.countByStatus("CONCLUIDA", mesAtual);
        int atividadesPendentes = atividadeRepository.countByStatus("EM_ANDAMENTO", mesAtual);
        int atividadesAbertas = atividadeRepository.countByStatus("ABERTA", mesAtual);
        int atividadesPausadas = atividadeRepository.countByStatus("PAUSADA", mesAtual);

        int usuariosAtivos = usuarioRepository.countUsuariosAtivos();

        Double somaHoras = horasRepository.sumTotalHorasPorMes(mesAtual);
        int totalHoras = somaHoras == null ? 0 : somaHoras.intValue();
        List<GraficoBarrasDTO> dadosGraficoBarras = horasRepository.sumHorasPorProjeto();

        return new DashboardAdminDTO(
            projetosConcluidos,
            projetosPendentes, 
            projetosPlanejados,
            projetosCancelados,
            atividadesConcluidas,
            atividadesPendentes, 
            atividadesAbertas,
            atividadesPausadas,
            usuariosAtivos,
            totalHoras, 
            dadosGraficoBarras
        );
    }

    /**
     * Busca métricas para o dashboard de usuários gerais, 
     * incluindo informações sobre projetos, atividades e horas trabalhadas.
     *
     * @param id ID do usuário.
     * @return DTO contendo as métricas gerais.
     */
    public DashboardGeneralDTO buscarMetricasGerais(int id) {
        int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int projetosPendentes = projetoRepository.countByStatusAndUsuario("EM_ANDAMENTO", id);
        int atividadesPendentes = atividadeRepository.countByStatusAndUsuario("EM_ANDAMENTO", id);
        
        List<GraficoDoughnutDTO> projPorPrioridade = projetoRepository.findByPrioridadeAndUsuario(id);
        List<GraficoDoughnutDTO> projPorStatus = projetoRepository.findByStatusAndUsuario(id);
        List<GraficoDoughnutDTO> ativPorStatus = atividadeRepository.findByStatusAndUsuario(id);

        Double somaHoras = horasRepository.sumHorasPorMesAndUsuario(mesAtual, id);
        int totalHoras = somaHoras == null ? 0 : somaHoras.intValue();

        return new DashboardGeneralDTO(
            projetosPendentes, 
            atividadesPendentes, 
            totalHoras,
            projPorPrioridade,
            projPorStatus,
            ativPorStatus);
    }
}