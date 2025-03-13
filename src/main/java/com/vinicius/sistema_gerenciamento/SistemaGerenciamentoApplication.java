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

		Usuario admin = new Usuario("João Pedro", "joao@workflow.com", encoder.encode("123"), "ADMIN");
        Usuario usuario1 = new Usuario("Maria Silva", "maria@workflow.com", encoder.encode("123"), "USUARIO");
        Usuario usuario2 = new Usuario("João Souza", "souza@workflow.com", encoder.encode("123"), "USUARIO");
        Usuario usuario3 = new Usuario("Carla Pereira", "carla@workflow.com", encoder.encode("123"), "USUARIO");
        Usuario usuario4 = new Usuario("Vinicius Santos", "vini@workflow.com", encoder.encode("123"), "USUARIO");

        List<Usuario> usuarios = Arrays.asList(admin, usuario1, usuario2, usuario3, usuario4);
        usuarioRepository.saveAll(usuarios);

    
        Projeto projeto1 = new Projeto("Sistema de Gerenciamento", "Desenvolver um sistema de gerenciamento de projetos", 
            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 25), "EM_ANDAMENTO", "ALTA", admin);
        Projeto projeto2 = new Projeto("Site de E-commerce", "Desenvolver um site de e-commerce para venda de produtos", 
            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 25), "EM_ANDAMENTO", "MEDIA", usuario1);
        Projeto projeto3 = new Projeto("App Mobile", "Desenvolver um aplicativo mobile para gerenciamento de tarefas", 
            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 25), "PLANEJADO", "BAIXA", admin);
        Projeto projeto4 = new Projeto("API de Pagamentos", "Desenvolver uma API para integração com gateways de pagamento", 
            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 25), "CONCLUIDO", "ALTA", usuario1);
        Projeto projeto5 = new Projeto("Portal de Notícias", "Desenvolver um portal de notícias com atualizações em tempo real", 
            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 25), "EM_ANDAMENTO", "MEDIA", admin);
        Projeto projeto6 = new Projeto("Sistema de RH", "Automatizar processos de recursos humanos", 
            LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 25), "CONCLUIDO", "ALTA", usuario1);


        List<Projeto> projetos = Arrays.asList(
			projeto1, projeto2, projeto3, projeto4, projeto5, projeto6
        );
        projetoRepository.saveAll(projetos);

        
        Atividade atividade1 = new Atividade("Desenvolver Login", "Implementar sistema de autenticação de usuários", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto1, admin);
        Atividade atividade2 = new Atividade("Criar Banco de Dados", "Modelar e implementar o banco de dados do sistema", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "CONCLUIDA", projeto2, usuario1);
        Atividade atividade3 = new Atividade("Desenvolver Carrinho", "Implementar funcionalidade de carrinho de compras", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025,4, 1), "EM_ANDAMENTO", projeto3, admin);
        Atividade atividade4 = new Atividade("Integrar Pagamento", "Integrar gateway de pagamento ao site", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "ABERTA", projeto4, usuario1);
        Atividade atividade5 = new Atividade("Criar Interface do App", "Desenvolver a interface do aplicativo mobile", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto5, admin);
        Atividade atividade6 = new Atividade("Testar API de Pagamentos", "Realizar testes na API de pagamentos", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "CONCLUIDA", projeto6, usuario1);

        Atividade atividade7 = new Atividade("Automatizar Folha de Pagamento", "Desenvolver módulo de folha de pagamento", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "CONCLUIDA", projeto1, admin);
        Atividade atividade8 = new Atividade("Criar Plataforma de Cursos", "Desenvolver a plataforma para cursos online", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "ABERTA", projeto2, usuario1);
        Atividade atividade9 = new Atividade("Analisar Dados", "Implementar ferramentas de análise de dados", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto3, admin);
        Atividade atividade10 = new Atividade("Monitorar Servidores", "Configurar ferramentas de monitoramento", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto4, usuario1);
        Atividade atividade11 = new Atividade("Desenvolver Carrinho de Compras", "Implementar funcionalidade de carrinho", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "ABERTA", projeto5, admin);
        Atividade atividade12 = new Atividade("Criar Dashboard Financeiro", "Desenvolver dashboard para controle financeiro", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto6, usuario1);

        Atividade atividade13 = new Atividade("Publicar Vagas de Emprego", "Implementar funcionalidade de publicação de vagas", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto1, admin);
        Atividade atividade14 = new Atividade("Automatizar Matrículas", "Desenvolver módulo de matrículas online", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "CONCLUIDA", projeto2, usuario1);
        Atividade atividade15 = new Atividade("Monitorar Saúde do Usuário", "Implementar funcionalidades de monitoramento", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto3, admin);
        Atividade atividade16 = new Atividade("Criar Plataforma de Eventos", "Desenvolver plataforma para gerenciamento de eventos", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "ABERTA", projeto4, usuario1);
        Atividade atividade17 = new Atividade("Automatizar Roteirização", "Implementar sistema de roteirização logística", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "EM_ANDAMENTO", projeto5, admin);
        Atividade atividade18 = new Atividade("Planejar Viagens", "Desenvolver funcionalidade de planejamento de viagens", 
            LocalDate.of(2025, 3, 14), LocalDate.of(2025, 4, 1), "PAUSADA", projeto6, usuario1);

        List<Atividade> atividades = Arrays.asList(
			atividade1, atividade2, atividade3, atividade4, atividade5, atividade6, atividade7, atividade8, atividade9, atividade10,
    	 	atividade11, atividade12, atividade13, atividade14, atividade15, atividade16, atividade17, atividade18);
        atividadeRepository.saveAll(atividades);

        
		LancamentoHora hora1 = new LancamentoHora("Desenvolvimento do Login", LocalTime.of(6, 0), LocalTime.of(8, 0), atividade1, admin);
		LancamentoHora hora2 = new LancamentoHora("Modelagem do Banco de Dados", LocalTime.of(8, 0), LocalTime.of(12, 0), atividade2, usuario1);
		LancamentoHora hora3 = new LancamentoHora("Implementação do Carrinho", LocalTime.of(6, 0), LocalTime.of(9, 0), atividade3, admin);
		LancamentoHora hora4 = new LancamentoHora("Integração com PagSeguro", LocalTime.of(12, 0), LocalTime.of(14, 0), atividade4, usuario1);
		LancamentoHora hora5 = new LancamentoHora("Criação da Interface", LocalTime.of(9, 0), LocalTime.of(11, 0), atividade5, admin);
		LancamentoHora hora6 = new LancamentoHora("Testes na API", LocalTime.of(8, 0), LocalTime.of(16, 0), atividade6, usuario1);
		LancamentoHora hora7 = new LancamentoHora("Publicação de Notícias", LocalTime.of(12, 0), LocalTime.of(17, 0), atividade7, admin);
		LancamentoHora hora8 = new LancamentoHora("Automatização da Folha", LocalTime.of(9, 0), LocalTime.of(11, 0), atividade8, usuario1);
		LancamentoHora hora9 = new LancamentoHora("Desenvolvimento da Plataforma", LocalTime.of(14, 0), LocalTime.of(19, 0), atividade9, admin);
		LancamentoHora hora10 = new LancamentoHora("Análise de Dados", LocalTime.of(12, 0), LocalTime.of(16, 0), atividade10, usuario1);
		LancamentoHora hora11 = new LancamentoHora("Monitoramento de Servidores", LocalTime.of(16, 0), LocalTime.of(18, 0), atividade11, admin);
		LancamentoHora hora12 = new LancamentoHora("Desenvolvimento do Carrinho", LocalTime.of(17, 0), LocalTime.of(18, 0), atividade12, usuario1);
		LancamentoHora hora13 = new LancamentoHora("Criação do Dashboard", LocalTime.of(11, 0), LocalTime.of(17, 0), atividade13, admin);
		LancamentoHora hora14 = new LancamentoHora("Automatização de Chamados", LocalTime.of(19, 0), LocalTime.of(20, 0), atividade14, usuario1);
		LancamentoHora hora15 = new LancamentoHora("Publicação de Vagas", LocalTime.of(16, 0), LocalTime.of(18, 0), atividade15, admin);
		LancamentoHora hora16 = new LancamentoHora("Automatização de Matrículas", LocalTime.of(18, 0), LocalTime.of(23, 0), atividade16, usuario1);
		LancamentoHora hora17 = new LancamentoHora("Monitoramento de Saúde", LocalTime.of(18, 0), LocalTime.of(19, 0), atividade17, admin);
		LancamentoHora hora18 = new LancamentoHora("Desenvolvimento da Plataforma", LocalTime.of(17, 0), LocalTime.of(18, 0), atividade18, usuario1);

        List<LancamentoHora> horaLancadas = Arrays.asList(
			hora1, hora2, hora3, hora4, hora5, hora6, hora7, hora8, hora9, hora10,
            hora11, hora12, hora13, hora14, hora15, hora16, hora17, hora18);
        horasRepository.saveAll(horaLancadas);

        
        UsuarioProjeto up1 = new UsuarioProjeto(admin, projeto1);
        UsuarioProjeto up2 = new UsuarioProjeto(usuario1, projeto2);
        UsuarioProjeto up3 = new UsuarioProjeto(admin, projeto3);
        UsuarioProjeto up4 = new UsuarioProjeto(usuario1, projeto4);
        UsuarioProjeto up5 = new UsuarioProjeto(admin, projeto5);
        UsuarioProjeto up6 = new UsuarioProjeto(usuario1, projeto6);
        UsuarioProjeto up7 = new UsuarioProjeto(usuario2, projeto1);
        UsuarioProjeto up8 = new UsuarioProjeto(usuario3, projeto2);
        UsuarioProjeto up9 = new UsuarioProjeto(usuario4, projeto3);
        UsuarioProjeto up10 = new UsuarioProjeto(usuario2, projeto4);
        UsuarioProjeto up11 = new UsuarioProjeto(usuario3, projeto5);
        UsuarioProjeto up12 = new UsuarioProjeto(usuario4, projeto6);

        List<UsuarioProjeto> integrantesProjeto = Arrays.asList(
			up1, up2, up3, up4, up5, up6, up7, up8, up9, up10,
			up11, up12
        );

        upRepository.saveAll(integrantesProjeto);

       
        UsuarioAtividade ua1 = new UsuarioAtividade(admin, atividade1);
        UsuarioAtividade ua2 = new UsuarioAtividade(usuario1, atividade2);
        UsuarioAtividade ua3 = new UsuarioAtividade(admin, atividade3);
        UsuarioAtividade ua4 = new UsuarioAtividade(usuario1, atividade4);
        UsuarioAtividade ua5 = new UsuarioAtividade(admin, atividade5);
        UsuarioAtividade ua6 = new UsuarioAtividade(usuario1, atividade6);
        UsuarioAtividade ua7 = new UsuarioAtividade(admin, atividade7);
        UsuarioAtividade ua8 = new UsuarioAtividade(usuario1, atividade8);
        UsuarioAtividade ua9 = new UsuarioAtividade(admin, atividade9);
        UsuarioAtividade ua10 = new UsuarioAtividade(usuario1, atividade10);
        UsuarioAtividade ua11 = new UsuarioAtividade(admin, atividade11);
        UsuarioAtividade ua12 = new UsuarioAtividade(usuario1, atividade12);
        UsuarioAtividade ua13 = new UsuarioAtividade(admin, atividade13);
        UsuarioAtividade ua14 = new UsuarioAtividade(usuario1, atividade14);
        UsuarioAtividade ua15 = new UsuarioAtividade(admin, atividade15);
        UsuarioAtividade ua16 = new UsuarioAtividade(usuario1, atividade16);
        UsuarioAtividade ua17 = new UsuarioAtividade(admin, atividade17);
        UsuarioAtividade ua18 = new UsuarioAtividade(usuario1, atividade18);

        List<UsuarioAtividade> integrantesAtividade = Arrays.asList(
			ua1, ua2, ua3, ua4, ua5, ua6, ua7, ua8, ua9, ua10,
			ua11, ua12, ua13, ua14, ua15, ua16, ua17, ua18);

        uaRepository.saveAll(integrantesAtividade);
    }
}
