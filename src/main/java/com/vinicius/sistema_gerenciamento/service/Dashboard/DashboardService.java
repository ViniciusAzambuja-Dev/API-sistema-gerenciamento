package com.vinicius.sistema_gerenciamento.service.Dashboard;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.DashboardAdminDTO;
import com.vinicius.sistema_gerenciamento.dto.response.Dashboard.ChartDatasDTO;
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

        int totalHoras = horasRepository.sumTotalHorasPorMes(mesAtual).intValue();
        List<ChartDatasDTO> chartDatas = projetoRepository.sumHorasPorProjeto();

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
            chartDatas
        );
    }
}
