package com.vinicius.sistema_gerenciamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.vinicius.sistema_gerenciamento.model.Atividade.Atividade;
import com.vinicius.sistema_gerenciamento.model.Horas.LancamentoHora;
import com.vinicius.sistema_gerenciamento.model.Projeto.Projeto;
import com.vinicius.sistema_gerenciamento.model.Usuario.Usuario;
import com.vinicius.sistema_gerenciamento.model.UsuarioAtividade.UsuarioAtividade;
import com.vinicius.sistema_gerenciamento.model.UsuarioProjeto.UsuarioProjeto;
import com.vinicius.sistema_gerenciamento.repository.Atividade.AtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.Horas.HorasRepository;
import com.vinicius.sistema_gerenciamento.repository.Projeto.ProjetoRepository;
import com.vinicius.sistema_gerenciamento.repository.Usuario.UsuarioRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioAtividade.UsuarioAtividadeRepository;
import com.vinicius.sistema_gerenciamento.repository.UsuarioProjeto.UsuarioProjetoRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class SistemaGerenciamentoApplication implements CommandLineRunner {

   private final UsuarioRepository usuarioRepository;
    private final ProjetoRepository projetoRepository;
    private final AtividadeRepository atividadeRepository;
    private final HorasRepository horasRepository;
    private final UsuarioProjetoRepository upRepository;
    private final UsuarioAtividadeRepository uaRepository;

    public static void main(String[] args) {
        SpringApplication.run(SistemaGerenciamentoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
      	
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Usuario admin = new Usuario("João Pedro", "admin@workflow.com", encoder.encode("trainee"), "ADMIN");
        Usuario usuario1 = new Usuario("Maria Silva", "usuario@workflow.com", encoder.encode("trainee"), "USUARIO");
        Usuario usuario2 = new Usuario("João Souza", "usuario2@workflow.com", encoder.encode("trainee"), "USUARIO");
        Usuario usuario3 = new Usuario("Carla Pereira", "usuario3@workflow.com", encoder.encode("trainee"), "USUARIO");
        Usuario usuario4 = new Usuario("Vinicius Santos", "usuario4@workflow.com", encoder.encode("trainee"), "USUARIO");

        List<Usuario> usuarios = Arrays.asList(admin, usuario1, usuario2, usuario3, usuario4);
        usuarioRepository.saveAll(usuarios);

       
        Projeto projeto1 = new Projeto("Sistema de Gerenciamento", "Desenvolver um sistema de gerenciamento de projetos", 
            LocalDate.of(2023, 10, 1), LocalDate.of(2023, 12, 31), "EM_ANDAMENTO", "ALTA", admin);
        Projeto projeto2 = new Projeto("Site de E-commerce", "Desenvolver um site de e-commerce para venda de produtos", 
            LocalDate.of(2023, 9, 15), LocalDate.of(2023, 11, 30), "EM_ANDAMENTO", "MEDIA", usuario1);
        Projeto projeto3 = new Projeto("App Mobile", "Desenvolver um aplicativo mobile para gerenciamento de tarefas", 
            LocalDate.of(2023, 10, 10), LocalDate.of(2024, 1, 15), "PLANEJADO", "BAIXA", usuario2);
        Projeto projeto4 = new Projeto("API de Pagamentos", "Desenvolver uma API para integração com gateways de pagamento", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2024, 2, 28), "CONCLUIDO", "ALTA", usuario3);
        Projeto projeto5 = new Projeto("Portal de Notícias", "Desenvolver um portal de notícias com atualizações em tempo real", 
            LocalDate.of(2023, 10, 5), LocalDate.of(2023, 12, 15), "EM_ANDAMENTO", "MEDIA", usuario4);
        Projeto projeto6 = new Projeto("Sistema de RH", "Automatizar processos de recursos humanos", 
            LocalDate.of(2023, 9, 20), LocalDate.of(2023, 11, 30), "CONCLUIDO", "ALTA", admin);
        Projeto projeto7 = new Projeto("Plataforma de Cursos Online", "Criar uma plataforma para cursos EAD", 
            LocalDate.of(2023, 10, 1), LocalDate.of(2024, 3, 31), "PLANEJADO", "BAIXA", usuario1);
        Projeto projeto8 = new Projeto("Ferramenta de Análise de Dados", "Desenvolver uma ferramenta para análise de dados", 
            LocalDate.of(2023, 10, 10), LocalDate.of(2024, 2, 28), "EM_ANDAMENTO", "ALTA", usuario2);
        Projeto projeto9 = new Projeto("Sistema de Reservas", "Criar um sistema para reservas de hotéis e restaurantes", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2024, 1, 31), "PLANEJADO", "MEDIA", usuario3);
        Projeto projeto10 = new Projeto("Aplicativo de Delivery", "Desenvolver um app para delivery de comida", 
            LocalDate.of(2023, 10, 15), LocalDate.of(2024, 2, 15), "CANCELADO", "ALTA", usuario4); // Projeto cancelado
        Projeto projeto11 = new Projeto("Sistema de Monitoramento", "Monitorar servidores e aplicações em tempo real", 
            LocalDate.of(2023, 10, 20), LocalDate.of(2024, 3, 31), "EM_ANDAMENTO", "ALTA", admin);
        Projeto projeto12 = new Projeto("Plataforma de Vendas", "Criar uma plataforma para vendas online", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2024, 2, 28), "PLANEJADO", "MEDIA", usuario1);
        Projeto projeto13 = new Projeto("App de Finanças Pessoais", "Desenvolver um app para controle financeiro", 
            LocalDate.of(2023, 10, 25), LocalDate.of(2024, 1, 31), "EM_ANDAMENTO", "ALTA", usuario2);
        Projeto projeto14 = new Projeto("Sistema de Chamados", "Automatizar abertura e acompanhamento de chamados", 
            LocalDate.of(2023, 11, 5), LocalDate.of(2024, 2, 15), "PLANEJADO", "BAIXA", usuario3);
        Projeto projeto15 = new Projeto("Portal de Vagas de Emprego", "Criar um portal para divulgação de vagas", 
            LocalDate.of(2023, 10, 30), LocalDate.of(2024, 3, 31), "EM_ANDAMENTO", "MEDIA", usuario4);
        Projeto projeto16 = new Projeto("Sistema de Gestão Escolar", "Automatizar processos de gestão escolar", 
            LocalDate.of(2023, 11, 10), LocalDate.of(2024, 2, 28), "CONCLUIDO", "ALTA", admin);
        Projeto projeto17 = new Projeto("App de Saúde", "Desenvolver um app para monitoramento de saúde", 
            LocalDate.of(2023, 10, 15), LocalDate.of(2024, 1, 31), "EM_ANDAMENTO", "ALTA", usuario1);
        Projeto projeto18 = new Projeto("Plataforma de Eventos", "Criar uma plataforma para gerenciamento de eventos", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2024, 2, 28), "PLANEJADO", "MEDIA", usuario2);
        Projeto projeto19 = new Projeto("Sistema de Logística", "Automatizar processos logísticos", 
            LocalDate.of(2023, 10, 20), LocalDate.of(2024, 3, 31), "EM_ANDAMENTO", "ALTA", usuario3);
        Projeto projeto20 = new Projeto("App de Turismo", "Desenvolver um app para planejamento de viagens", 
            LocalDate.of(2023, 11, 5), LocalDate.of(2024, 2, 15), "CANCELADO", "BAIXA", usuario4); // Projeto cancelado

        List<Projeto> projetos = Arrays.asList(
			projeto1, projeto2, projeto3, projeto4, projeto5, projeto6, projeto7, projeto8, projeto9, projeto10,
            projeto11, projeto12, projeto13, projeto14, projeto15, projeto16, projeto17, projeto18, projeto19, projeto20);
        projetoRepository.saveAll(projetos);

        
        Atividade atividade1 = new Atividade("Desenvolver Login", "Implementar sistema de autenticação de usuários", 
            LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 10), "EM_ANDAMENTO", projeto1, admin);
        Atividade atividade2 = new Atividade("Criar Banco de Dados", "Modelar e implementar o banco de dados do sistema", 
            LocalDate.of(2023, 10, 5), LocalDate.of(2023, 10, 15), "CONCLUIDA", projeto1, usuario1);
        Atividade atividade3 = new Atividade("Desenvolver Carrinho", "Implementar funcionalidade de carrinho de compras", 
            LocalDate.of(2023, 9, 20), LocalDate.of(2023, 10, 5), "EM_ANDAMENTO", projeto2, usuario2);
        Atividade atividade4 = new Atividade("Integrar Pagamento", "Integrar gateway de pagamento ao site", 
            LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 20), "ABERTA", projeto2, usuario3);
        Atividade atividade5 = new Atividade("Criar Interface do App", "Desenvolver a interface do aplicativo mobile", 
            LocalDate.of(2023, 10, 10), LocalDate.of(2023, 10, 25), "EM_ANDAMENTO", projeto3, usuario4);
        Atividade atividade6 = new Atividade("Testar API de Pagamentos", "Realizar testes na API de pagamentos", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 10), "CONCLUIDA", projeto4, admin);
        Atividade atividade7 = new Atividade("Publicar Notícias", "Implementar funcionalidade de publicação de notícias", 
            LocalDate.of(2023, 10, 5), LocalDate.of(2023, 10, 20), "EM_ANDAMENTO", projeto5, usuario1);
        Atividade atividade8 = new Atividade("Automatizar Folha de Pagamento", "Desenvolver módulo de folha de pagamento", 
            LocalDate.of(2023, 9, 20), LocalDate.of(2023, 10, 10), "CONCLUIDA", projeto6, usuario2);
        Atividade atividade9 = new Atividade("Criar Plataforma de Cursos", "Desenvolver a plataforma para cursos online", 
            LocalDate.of(2023, 10, 1), LocalDate.of(2023, 12, 31), "ABERTA", projeto7, usuario3);
        Atividade atividade10 = new Atividade("Analisar Dados", "Implementar ferramentas de análise de dados", 
            LocalDate.of(2023, 10, 10), LocalDate.of(2023, 11, 30), "EM_ANDAMENTO", projeto8, usuario4);
        Atividade atividade11 = new Atividade("Monitorar Servidores", "Configurar ferramentas de monitoramento", 
            LocalDate.of(2023, 10, 20), LocalDate.of(2023, 11, 15), "EM_ANDAMENTO", projeto11, admin);
        Atividade atividade12 = new Atividade("Desenvolver Carrinho de Compras", "Implementar funcionalidade de carrinho", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2023, 11, 20), "ABERTA", projeto12, usuario1);
        Atividade atividade13 = new Atividade("Criar Dashboard Financeiro", "Desenvolver dashboard para controle financeiro", 
            LocalDate.of(2023, 10, 25), LocalDate.of(2023, 11, 10), "EM_ANDAMENTO", projeto13, usuario2);
        Atividade atividade14 = new Atividade("Automatizar Abertura de Chamados", "Implementar sistema de abertura de chamados", 
            LocalDate.of(2023, 11, 5), LocalDate.of(2023, 11, 20), "ABERTA", projeto14, usuario3);
        Atividade atividade15 = new Atividade("Publicar Vagas de Emprego", "Implementar funcionalidade de publicação de vagas", 
            LocalDate.of(2023, 10, 30), LocalDate.of(2023, 11, 15), "EM_ANDAMENTO", projeto15, usuario4);
        Atividade atividade16 = new Atividade("Automatizar Matrículas", "Desenvolver módulo de matrículas online", 
            LocalDate.of(2023, 11, 10), LocalDate.of(2023, 11, 30), "CONCLUIDA", projeto16, admin);
        Atividade atividade17 = new Atividade("Monitorar Saúde do Usuário", "Implementar funcionalidades de monitoramento", 
            LocalDate.of(2023, 10, 15), LocalDate.of(2023, 11, 10), "EM_ANDAMENTO", projeto17, usuario1);
        Atividade atividade18 = new Atividade("Criar Plataforma de Eventos", "Desenvolver plataforma para gerenciamento de eventos", 
            LocalDate.of(2023, 11, 1), LocalDate.of(2023, 12, 31), "ABERTA", projeto18, usuario2);
        Atividade atividade19 = new Atividade("Automatizar Roteirização", "Implementar sistema de roteirização logística", 
            LocalDate.of(2023, 10, 20), LocalDate.of(2023, 11, 15), "EM_ANDAMENTO", projeto19, usuario3);
        Atividade atividade20 = new Atividade("Planejar Viagens", "Desenvolver funcionalidade de planejamento de viagens", 
            LocalDate.of(2023, 11, 5), LocalDate.of(2023, 11, 20), "PAUSADA", projeto20, usuario4); // Atividade pausada (projeto cancelado)

        List<Atividade> atividades = Arrays.asList(
			atividade1, atividade2, atividade3, atividade4, atividade5, atividade6, atividade7, atividade8, atividade9, atividade10,
    	 	atividade11, atividade12, atividade13, atividade14, atividade15, atividade16, atividade17, atividade18, atividade19, atividade20);
        atividadeRepository.saveAll(atividades);

        
		LancamentoHora hora1 = new LancamentoHora("Desenvolvimento do Login", LocalTime.of(6, 0), LocalTime.of(8, 0), atividade1, admin);
		LancamentoHora hora2 = new LancamentoHora("Modelagem do Banco de Dados", LocalTime.of(8, 0), LocalTime.of(12, 0), atividade2, usuario1);
		LancamentoHora hora3 = new LancamentoHora("Implementação do Carrinho", LocalTime.of(6, 0), LocalTime.of(9, 0), atividade3, usuario2);
		LancamentoHora hora4 = new LancamentoHora("Integração com PagSeguro", LocalTime.of(12, 0), LocalTime.of(14, 0), atividade4, usuario3);
		LancamentoHora hora5 = new LancamentoHora("Criação da Interface", LocalTime.of(9, 0), LocalTime.of(11, 0), atividade5, usuario4);
		LancamentoHora hora6 = new LancamentoHora("Testes na API", LocalTime.of(8, 0), LocalTime.of(16, 0), atividade6, admin);
		LancamentoHora hora7 = new LancamentoHora("Publicação de Notícias", LocalTime.of(12, 0), LocalTime.of(17, 0), atividade7, usuario1);
		LancamentoHora hora8 = new LancamentoHora("Automatização da Folha", LocalTime.of(9, 0), LocalTime.of(11, 0), atividade8, usuario2);
		LancamentoHora hora9 = new LancamentoHora("Desenvolvimento da Plataforma", LocalTime.of(14, 0), LocalTime.of(19, 0), atividade9, usuario3);
		LancamentoHora hora10 = new LancamentoHora("Análise de Dados", LocalTime.of(12, 0), LocalTime.of(16, 0), atividade10, usuario4);
		LancamentoHora hora11 = new LancamentoHora("Monitoramento de Servidores", LocalTime.of(16, 0), LocalTime.of(18, 0), atividade11, admin);
		LancamentoHora hora12 = new LancamentoHora("Desenvolvimento do Carrinho", LocalTime.of(17, 0), LocalTime.of(18, 0), atividade12, usuario1);
		LancamentoHora hora13 = new LancamentoHora("Criação do Dashboard", LocalTime.of(11, 0), LocalTime.of(17, 0), atividade13, usuario2);
		LancamentoHora hora14 = new LancamentoHora("Automatização de Chamados", LocalTime.of(19, 0), LocalTime.of(20, 0), atividade14, usuario3);
		LancamentoHora hora15 = new LancamentoHora("Publicação de Vagas", LocalTime.of(16, 0), LocalTime.of(18, 0), atividade15, usuario4);
		LancamentoHora hora16 = new LancamentoHora("Automatização de Matrículas", LocalTime.of(18, 0), LocalTime.of(23, 0), atividade16, admin);
		LancamentoHora hora17 = new LancamentoHora("Monitoramento de Saúde", LocalTime.of(18, 0), LocalTime.of(19, 0), atividade17, usuario1);
		LancamentoHora hora18 = new LancamentoHora("Desenvolvimento da Plataforma", LocalTime.of(17, 0), LocalTime.of(18, 0), atividade18, usuario2);
		LancamentoHora hora19 = new LancamentoHora("Automatização de Roteirização", LocalTime.of(20, 0), LocalTime.of(23, 0), atividade19, usuario3);
		LancamentoHora hora20 = new LancamentoHora("Planejamento de Viagens", LocalTime.of(18, 0), LocalTime.of(20, 0), atividade20, usuario4);

        List<LancamentoHora> horaLancadas = Arrays.asList(
			hora1, hora2, hora3, hora4, hora5, hora6, hora7, hora8, hora9, hora10,
            hora11, hora12, hora13, hora14, hora15, hora16, hora17, hora18, hora19, hora20);
        horasRepository.saveAll(horaLancadas);

        
        UsuarioProjeto up1 = new UsuarioProjeto(admin, projeto1);
        UsuarioProjeto up2 = new UsuarioProjeto(usuario1, projeto1);
        UsuarioProjeto up3 = new UsuarioProjeto(usuario2, projeto2);
        UsuarioProjeto up4 = new UsuarioProjeto(usuario3, projeto2);
        UsuarioProjeto up5 = new UsuarioProjeto(usuario4, projeto3);
        UsuarioProjeto up6 = new UsuarioProjeto(admin, projeto4);
        UsuarioProjeto up7 = new UsuarioProjeto(usuario1, projeto5);
        UsuarioProjeto up8 = new UsuarioProjeto(usuario2, projeto6);
        UsuarioProjeto up9 = new UsuarioProjeto(usuario3, projeto7);
        UsuarioProjeto up10 = new UsuarioProjeto(usuario4, projeto8);
        UsuarioProjeto up11 = new UsuarioProjeto(admin, projeto9);
        UsuarioProjeto up12 = new UsuarioProjeto(usuario1, projeto10);
        UsuarioProjeto up13 = new UsuarioProjeto(usuario2, projeto11);
        UsuarioProjeto up14 = new UsuarioProjeto(usuario3, projeto12);
        UsuarioProjeto up15 = new UsuarioProjeto(usuario4, projeto13);
        UsuarioProjeto up16 = new UsuarioProjeto(admin, projeto14);
        UsuarioProjeto up17 = new UsuarioProjeto(usuario1, projeto15);
        UsuarioProjeto up18 = new UsuarioProjeto(usuario2, projeto16);
        UsuarioProjeto up19 = new UsuarioProjeto(usuario3, projeto17);
        UsuarioProjeto up20 = new UsuarioProjeto(usuario4, projeto18);

        List<UsuarioProjeto> integrantesProjeto = Arrays.asList(
			up1, up2, up3, up4, up5, up6, up7, up8, up9, up10,
			up11, up12, up13, up14, up15, up16, up17, up18, up19, up20);

        upRepository.saveAll(integrantesProjeto);

       
        UsuarioAtividade ua1 = new UsuarioAtividade(admin, atividade1);
        UsuarioAtividade ua2 = new UsuarioAtividade(usuario1, atividade2);
        UsuarioAtividade ua3 = new UsuarioAtividade(usuario2, atividade3);
        UsuarioAtividade ua4 = new UsuarioAtividade(usuario3, atividade4);
        UsuarioAtividade ua5 = new UsuarioAtividade(usuario4, atividade5);
        UsuarioAtividade ua6 = new UsuarioAtividade(admin, atividade6);
        UsuarioAtividade ua7 = new UsuarioAtividade(usuario1, atividade7);
        UsuarioAtividade ua8 = new UsuarioAtividade(usuario2, atividade8);
        UsuarioAtividade ua9 = new UsuarioAtividade(usuario3, atividade9);
        UsuarioAtividade ua10 = new UsuarioAtividade(usuario4, atividade10);
        UsuarioAtividade ua11 = new UsuarioAtividade(admin, atividade11);
        UsuarioAtividade ua12 = new UsuarioAtividade(usuario1, atividade12);
        UsuarioAtividade ua13 = new UsuarioAtividade(usuario2, atividade13);
        UsuarioAtividade ua14 = new UsuarioAtividade(usuario3, atividade14);
        UsuarioAtividade ua15 = new UsuarioAtividade(usuario4, atividade15);
        UsuarioAtividade ua16 = new UsuarioAtividade(admin, atividade16);
        UsuarioAtividade ua17 = new UsuarioAtividade(usuario1, atividade17);
        UsuarioAtividade ua18 = new UsuarioAtividade(usuario2, atividade18);
        UsuarioAtividade ua19 = new UsuarioAtividade(usuario3, atividade19);
        UsuarioAtividade ua20 = new UsuarioAtividade(usuario4, atividade20);

        List<UsuarioAtividade> integrantesAtividade = Arrays.asList(
			ua1, ua2, ua3, ua4, ua5, ua6, ua7, ua8, ua9, ua10,
			ua11, ua12, ua13, ua14, ua15, ua16, ua17, ua18, ua19, ua20);

        uaRepository.saveAll(integrantesAtividade);
    }
}
