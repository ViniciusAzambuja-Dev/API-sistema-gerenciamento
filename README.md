# Workflow - Trainee Wise 2025

O Workflow é um sistema de gerenciamento de horas trabalhadas, onde usuários podem criar projetos, registrar atividades e lançar horas nessas atividades de forma organizada.

O objetivo é facilitar o controle do tempo investido em diferentes tarefas, permitindo um acompanhamento eficiente da produtividade. O sistema conta com autenticação de usuários e diferentes níveis de acesso, garantindo segurança e controle sobre os lançamentos.

## Funcionalidades

- **Gerenciamento de Tempo**: Controle das horas dedicadas a projetos e tarefas, permitindo lançamentos precisos e atualizações constantes.

- **Organização de Tarefas**: Criação e atribuição de tarefas para membros da equipe, com acompanhamento em tempo real do progresso e status de cada uma.

- **Registro de Horas**: Armazenamento detalhado das horas trabalhadas em tarefas e projetos, com fácil acesso a históricos e relatórios.

- **Visualização de Dados**: Painel de visualização para analisar o tempo gasto em projetos e tarefas, facilitando a tomada de decisões e ajustes.

- **Autenticação e Acesso**: Sistema de login para garantir que apenas usuários autorizados acessem funcionalidades específicas, com controle de permissões.

- **Gestão de Projetos**: Criação e administração de diversos projetos simultaneamente, com suporte para detalhamento e personalização de cada um.

## Tecnologias

Este projeto utiliza as seguintes tecnologias:

- **Frontend:** Angular
- **Backend:** Java Spring Boot
- **Banco de Dados:** MySQL

## Instalação

Siga os passos abaixo para rodar o projeto localmente:

### Pré-requisitos

Certifique-se de que você tem as seguintes ferramentas instaladas:

- [Java 11.0.22 ou 17](https://www.oracle.com/br/java/technologies/downloads/)
- [MySQL 8.0.34](https://dev.mysql.com/downloads/installer/)

### Configuração do Backend

#### 1. Clonar o Repositório

Clone o repositório da **API** para o seu ambiente local.

```bash
git clone https://github.com/ViniciusAzambuja-Dev/API-sistema-gerenciamento.git
cd API-sistema-gerenciamento
```

#### 2. Configurar o Banco de Dados

Para configurar o banco de dados, você precisará ter: 
- MySQL instalado na sua máquina.
- Substituir o root e a senha no arquivo **application.properties**
- Rodar na porta 3306.

#### 2. Rodar a aplicação
- Após configurar o Banco de dados, rode a aplicação e automaticamente registros pré-cadastrados serão criados.

### **Importante:**

Login ADMIN:
- Email: 'joao@workflow.com'
- Senha: 123

Login USUARIO:
- Email: 'maria@workflow.com'
- Senha: 123

 **E-mails**, **senhas** e todos os dados pré-cadastrados podem ser encontrados diretamente no arquivo principal que roda a aplicação, localizado em `SistemaGerenciamentoApplication.java`. Se precisar verificar ou alterar as credenciais dos usuários, basta editar as variáveis nesse arquivo.