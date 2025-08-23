# Termo de abertura

Nome do Projeto: Sistema de agendamento de salas do Politécnico

Cliente : Colégio Politécnico

Patrocinador do Projeto: Henry Cagnini

Equipe de desenvolvimento: Lorenzo Feltrin, Leticia Zanini, Pedro Ottavio,Vinicius Woltmann e Alessandro Chamorra

Previsão de Início e Término: 13/08/2025  03/12/2025

## Descrição da motivação e justificativa :

Atualmente, o processo de reserva de salas no Politécnico ocorre de forma manual, descentralizada e pouco transparente, o que gera conflitos de agendamento, indisponibilidade não registrada e perda de tempo tanto para alunos quanto para professores e administradores.

Com o aumento do número de turmas, projetos e atividades extracurriculares, a falta de um sistema estruturado dificulta o gerenciamento eficiente dos espaços. Além disso, os usuários precisam verificar pessoalmente ou via terceiros a disponibilidade das salas, o que compromete a organização e a experiência acadêmica.

A implementação de um sistema web de agendamento de salas justifica-se pela necessidade de **centralizar informações**, **reduzir conflitos**, **automatizar aprovações** e **garantir transparência** no uso dos espaços. O sistema também permitirá **controle de permissões** (alunos, professores e administradores), **notificações automáticas** e **melhor aproveitamento da infraestrutura existente**. Dessa forma, atende a uma demanda real do ambiente acadêmico, além de servir como aplicação prática dos conhecimentos de Engenharia de Software, cobrindo levantamento de requisitos, modelagem, implementação e validação.

## Objetivo(SMART):

- **Específico:** Desenvolver um sistema web de agendamento de salas para o Politécnico, permitindo reserva, aprovação e gerenciamento com base em perfis de usuário (aluno, professor, administrador).
- **Mensurável:** Garantir que 100% dos agendamentos sejam registrados no sistema.
- **Atingível:** O projeto será implementado em até **3 meses**, por uma equipe acadêmica, utilizando tecnologias acessíveis (Spring Boot, PostgreSQL e Angular).
- **Relevante:** O sistema melhora a organização do uso de salas, elimina conflitos e aumenta a transparência para toda a comunidade acadêmica.
- **Temporal:** A primeira versão funcional (MVP) deve estar disponível em até **12 semanas**, com funcionalidades de login, agendamento, aprovação, fila e notificações in-app.

## Descrição Resumida do Projeto:

O projeto consiste no desenvolvimento de um **sistema web de agendamento de salas** para o Politécnico. A aplicação permitirá visualizar a disponibilidade de salas em um **mapa interativo**, realizar **buscas filtradas**, acessar o **calendário de cada sala** e gerenciar reservas por meio de **fila de agendamento**.

O sistema implementará diferentes **níveis de permissão** (aluno, professor e administrador), cada um com responsabilidades específicas no fluxo de reserva.

Adicionalmente, o sistema contará com **notificações automáticas** (in-app e por e-mail) para manter os usuários atualizados sobre mudanças nos agendamentos, além de permitir a **personalização da interface** com tema claro e escuro.

A solução será projetada com base em princípios de Engenharia de Software, contemplando levantamento de requisitos, modelagem ER, definição de regras de negócio, arquitetura modular por contexto e documentação completa do processo de desenvolvimento.

## Entregas principais (requisitos):

Legenda:

(RF) : Requisito Funcional

(RNF) : Requisito Não Funcional

(RN) : Regra de Negócio

### Requisitos Funcionais (RF)

**Página Inicial & Busca**

- **RF01**: O sistema deve exibir na **página inicial** um mapa/grade do Politécnico com a quantidade de salas disponíveis por bloco.
- **RF02**: O sistema deve permitir aplicar **filtros** (disponibilidade, data, horário, bloco e andar) na tela inicial.
- **RF03**: O sistema deve permitir a busca de uma sala específica por **nome ou código**.
- **RF04**: Caso a sala seja encontrada, o sistema deve redirecionar o usuário para a **página detalhada da sala**.
- **RF05**: Caso a sala não seja encontrada, o sistema deve exibir a mensagem **“Sala não encontrada”**.

**Página da Sala & Calendário**

- **RF06**: O sistema deve exibir uma **página exclusiva para cada sala**, contendo calendário, agenda e fila de espera
- **RF07**: O sistema deve permitir que o usuário visualize o **calendário de ocupação** da sala, com diferenciação entre horários livres, ocupados e pendentes.
- **RF08**: O sistema deve permitir que o usuário entre em uma **fila de agendamento** para horários já ocupados.
- **RF09**: O sistema deve registrar e exibir a **ordem da fila** de agendamento.

**Agendamentos**

- **RF10**: O sistema deve permitir que **alunos** solicitem salas
- **RF11**: O sistema deve permitir que **professores** agendem salas diretamente, sem a necessidade de serem avaliados por um administrador
- **RF12**: O sistema deve permitir que **administradores** aprovem, rejeitem ou cancelem agendamentos criados por alunos, e cancelem agendamentos de professores.
- **RF13**: O sistema deve atualizar o calendário e a fila em tempo real (ou após atualização da página) após criação, aprovação ou cancelamento de um agendamento.

**Notificações**

- **RF14**: O sistema deve possuir uma **página de notificações**, acessível a todos os usuários.
- **RF15**: O sistema deve notificar usuários em caso de:
    - Aprovação ou rejeição de pedido de agendamento.
    - Cancelamento de agendamento.
    - Liberação de horário desejado.

**Autenticação & Permissões**

- **RF16**: O sistema deve permitir login de **alunos** via número de matrícula.
- **RF17**: O sistema deve permitir login de **professores e administradores** via número de contrato.
- **RF18**: O sistema deve identificar automaticamente o **papel do usuário** após login (Aluno, Professor ou Administrador).
- **RF19**: O sistema deve restringir funcionalidades de acordo com o **nível de permissão**.

**Outros**

- **RF20**: O sistema deve permitir alternar entre **tema claro e escuro**, com persistência da escolha.

### Requisitos Não Funcionais (RNF)

- **RNF01**: O sistema deve responder à aplicação de filtros na página inicial em até **2 segundos**.
- **RNF02**: O sistema deve registrar **logs de auditoria** para ações de administradores.
- **RNF03**: O sistema deve ser compatível com navegadores modernos em **desktop** e adaptável para **mobile**.

### Regras de Negócio (RN)

- **RN01**: Alunos podem apenas solicitar salas , sujeitos à aprovação de administrador.
- **RN02**: Professores podem agendar salas diretamente, sem necessidade de aprovação.
- **RN03**: Administradores podem criar, aprovar, rejeitar e cancelar qualquer agendamento.
- **RN04**: Não pode haver **sobreposição** de dois agendamentos efetivos (**AGENDA**) para a mesma sala no mesmo intervalo de tempo.
- **RN05**: Quando um horário é liberado, o sistema deve promover o próximo usuário na **fila de agendamento** ou notificá-lo para confirmar o interesse.
- **RN06**: O sistema deve validar agendamentos para que:
    - Não sejam criados em horários já passados.
    - Respeitem **horário de funcionamento** da instituição.
    - Sigam limites de duração mínima e máxima definidos pelo administrador.
- **RN07**: Toda alteração no status de um pedido ou agenda deve gerar uma **notificação** para o usuário envolvido.

## Recursos pré-alocados(quantos e quais recursos serão fornecidos):

Uma equipe de 5 desenvolvedores  responsáveis por todas as etapas do projeto, desde o planejamento até a implementação e testes.

**Tempo de dedicação estimado** de aproximadamente 3 **meses**, com carga horária variável conforme a disponibilidade dos desenvolvedores.

**Equipamento pessoal**: **desktops** com ambiente de desenvolvimento configurado.

**Ferramentas gratuitas utilizadas**: **IntelliJ IDEA (versão educacional), Figma, PostgreSQL, Spring Boot** e **GitHub**.

**Acesso direto ao usuário final** para coleta de requisitos, validação e feedback contínuo.

## Stakeholders:

Equipe de desenvolvimento
Henry Cagnini (avaliador acadêmico)

Colégio Politécnico

## Premissas e restrições:

## Premissas:

- O avaliador acadêmico Henry Cagnini  estará disponível para fornecer feedback ao longo do desenvolvimento.
- O projeto contara com uma equipe de 5 desenvolvedores
- A conexão com o banco de dados será estável e disponível durante os testes.
- Os desenvolvedores terão tempo semanal para se dedicar ao projeto.
- Os requisitos informados inicialmente não sofrerão alterações drásticas.

### Restrições:

- O sistema deve ser entregue em até 3 meses.
- Apenas ferramentas gratuitas ou com licenças educacionais podem ser utilizadas.
- O sistema deve rodar via navegador (web-based).