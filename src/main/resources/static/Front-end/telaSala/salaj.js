// =========================================================================
// VARIÁVEIS DE DADOS MOCK (ADAPTADAS PARA O FULLCALENDAR)
// Dados com novos nomes (Bloco A, B, C...) e eventos na S101.
// =========================================================================
const mockData = [
    { 
        bloco: "Bloco A", // Blocos A a G para o primeiro dropdown
        salas: [
            { 
                codigo: "S101", 
                nome: "Sala Multiuso 101", 
                capacidade: 30, 
                agenda: [ 
                  // EVENTOS MOCK JÁ AGENDADOS
                  { 
                      title: 'Evento Dia Todo', 
                      start: '2025-10-01', 
                      solicitante: 'João Silva', 
                      description: 'Preparação do ambiente para a mudança de mês.' 
                  },
                  { 
                      title: 'Dia das Crianças e Padroeira', 
                      start: '2025-10-12', 
                      solicitante: 'Prefeitura', 
                      description: 'Feriado Nacional. Não haverá expediente.' 
                  },
                  { 
                      title: 'Clique para Google', 
                      url: 'https://google.com/', 
                      start: '2025-10-28', 
                      solicitante: 'Equipe de TI', 
                      description: 'Revisão final de segurança do site.' 
                  },
                  { 
                      title: 'Reunião Importante', 
                      start: '2025-10-12T10:30:00', 
                      end: '2025-10-12T12:30:00', 
                      solicitante: 'Maria Oliveira', 
                      description: 'Definir o orçamento do próximo trimestre.' 
                  }
                ] 
            },
            { 
                codigo: "S105", 
                nome: "Laboratório de Informática 205", 
                capacidade: 20, 
                agenda: [
                    { title: "Experimento de DHCP", start: "2025-10-09T14:00:00", end: "2025-10-09T17:00:00", solicitante: "Pesquisador João Silva", description: "Configuração e testes em campo." }
                ] 
            },
        ]
    },
    { 
        bloco: "Bloco B", 
        salas: [
            { 
                codigo: "S201", 
                nome: "Auditório Central", 
                capacidade: 150, 
                agenda: [
                    { title: "Semana Acadêmica", start: "2025-10-10T08:00:00", end: "2025-10-10T18:00:00", solicitante: "Coordenação Geral", description: "Palestras e apresentações de artigos." }
                ] 
            },
            { codigo: "S203", nome: "Sala de Reuniões Pequena", capacidade: 10, agenda: [] },
        ]
    },
    { 
        bloco: "Bloco C", 
        salas: [
            { codigo: "S310", nome: "Sala de Treinamento", capacidade: 25, agenda: [] },
            { 
                codigo: "S301", 
                nome: "Laboratório de Ciências", 
                capacidade: 40, 
                agenda: [
                    { title: "Experimento de Eletricidade", start: "2025-10-08T10:00:00", end: "2025-10-08T13:00:00", solicitante: "Monitor Pedro", description: "Montagem do circuito RLC." }
                ] 
            },
        ]
    },
    { bloco: "Bloco D", salas: [{ codigo: "S405", nome: "Mini Auditório", capacidade: 50, agenda: [] }] }, 
    { bloco: "Bloco E", salas: [{ codigo: "S500", nome: "Lab de Química Analítica", capacidade: 45, agenda: [] }] }, 
    { bloco: "Bloco F", salas: [{ codigo: "S610", nome: "Sala de Computação", capacidade: 50, agenda: [] }] }, 
    { bloco: "Bloco G", salas: [{ codigo: "S705", nome: "Sala de Artes Visuais", capacidade: 25, agenda: [] }] } 
];


// =========================================================================
// VARIÁVEIS DE ESTADO E UTILS
// =========================================================================

let calendarInitialized = false; 
const DEFAULT_EVENT_COLOR = '#0070c0'; // ufsm_light

let currentBlockCode = '';
let currentSalaCode = '';
let currentSalaAgenda = [];

/**
 * Função que fecha o popup ao clicar em qualquer lugar dentro dele.
 */
function enablePopupClose($eventElement) {
    setTimeout(function() {
        var $popupModule = $eventElement.popup('get popup');
        if ($popupModule.length) {
            $popupModule.off('click.closePopup').on('click.closePopup', function(e) {
                e.stopPropagation(); 
                $eventElement.popup('hide');
                $eventElement.popup('destroy'); 
            });
        }
    }, 50);
}

// =========================================================================
// LÓGICA DO FULLCALENDAR 
// =========================================================================

/**
 * Inicializa ou atualiza o FullCalendar na div #calendar
 */
function initializeFullCalendar(events = []) {
    const $calendar = $('#calendar');

    // Se já foi inicializado, apenas atualiza a fonte de eventos
    if (calendarInitialized) {
        $calendar.fullCalendar('removeEvents');
        const coloredEvents = events.map(event => ({
            ...event,
            color: event.color || DEFAULT_EVENT_COLOR 
        }));
        $calendar.fullCalendar('addEventSource', coloredEvents);
        $calendar.fullCalendar('rerenderEvents');
        return;
    }
    
    const initialEvents = events.map(event => ({
        ...event,
        color: event.color || DEFAULT_EVENT_COLOR 
    }));

    // Inicialização do FullCalendar
    $calendar.fullCalendar({
        locale: 'pt-br',
        header: {
            // Configuração adaptada: basicWeek e basicDay para melhor experiência mobile
            left: 'prev,next today',
            center: 'title',
            right: 'month,basicWeek,basicDay' 
        },
        defaultView: 'month', // Volta para month como padrão para telas menores
        defaultDate: '2025-10-06', 
        minTime: '07:00:00', 
        maxTime: '22:00:00', 
        navLinks: true, 
        editable: false, 
        eventLimit: true, 
        validRange: {
             start: '2025-10-01'
        },
        
        events: initialEvents, 
        
        // Renderização do evento (Estilização)
        eventRender: function(event, element) {
            element.addClass('occupied-slot'); 
            element.css('background-color', event.color || DEFAULT_EVENT_COLOR); 
            element.css('border-color', event.color || DEFAULT_EVENT_COLOR);

            // Adiciona listener para destruir popup ao rolar a tela, para evitar fantasmas
             $('.fc-scroller').off('scroll.popup').on('scroll.popup', function() {
                 $(element).popup('destroy');
             });
        },
        
        // Ação ao clicar no evento (Exibir Tooltip/Popup)
        eventClick: function(calEvent, jsEvent, view) {
            var $eventElement = $(this);
            $eventElement.popup('destroy'); 
            
            var start = calEvent.start.format('DD/MM [às] HH:mm');
            var end = calEvent.end ? calEvent.end.format('DD/MM [às] HH:mm') : 'N/A';
            
            var solicitante = calEvent.solicitante ? calEvent.solicitante : 'Não informado';
            var description = calEvent.description ? calEvent.description : 'Nenhuma descrição fornecida.';
            
            var popupContentHTML = 
                '<div class="p-2">' +
                    '<div class="header text-lg font-bold mb-2 border-b pb-1 text-ufsm">Detalhes do Agendamento</div>' + 
                    '<div class="content text-gray-700 text-sm">' +
                        '<p class="mb-1"><strong>Evento:</strong> ' + calEvent.title + '</p>' + 
                        '<p class="mb-1"><strong>Solicitante:</strong> ' + solicitante + '</p>' + 
                        '<p class="mb-1"><strong>Descrição:</strong> ' + description + '</p>' + 
                        '<p class="mb-1"><strong>Início:</strong> ' + start + '</p>' +
                        '<p class="mb-1"><strong>Fim:</strong> ' + end + '</p>' +
                        (calEvent.url ? '<p class="mt-2"><a href="' + calEvent.url + '" target="_blank" class="text-ufsm hover:underline font-bold">Ver Detalhes (link)</a></p>' : '') +
                    '</div>' +
                '</div>';
            
            $eventElement.popup({
                html: popupContentHTML,
                position: 'bottom center', 
                on: 'manual', 
                onHidden: function() {
                    $eventElement.popup('destroy'); 
                },
            });

            $eventElement.popup('show');
            enablePopupClose($eventElement); 
            
            return false; 
        },
        
        eventLimitText: function(n) {
            return `+ ${n} mais`;
        }
    });
    
    calendarInitialized = true;
    document.getElementById('search-alert').textContent = "Selecione uma sala para visualizar sua agenda.";
}


// =========================================================================
// LÓGICA DE BUSCA E FILTRAGEM
// =========================================================================

/* Substitui as funções de populaçao/consulta para usar o backend via fetch,
   com fallback para mockData já presente no arquivo. */

async function fetchSalasFromApi() {

    //mudança recente aqui
    try {
        const res = await fetch('/salas/listar');
 // endpoint REST (ver sugestões abaixo)
        if (!res.ok) throw new Error('HTTP ' + res.status);
        const salas = await res.json(); // espera [{codigo, nome, bloco, capacidade}, ...] ou agrupado
        return salas;
    } catch (e) {
        console.warn('Não foi possível obter salas do backend, usando mock:', e);
        // Converte mockData para lista simples de salas mantendo bloco
        const list = [];
        mockData.forEach(b => b.salas.forEach(s => list.push({...s, bloco: b.bloco})));
        return list;
    }
}

async function popularSelectBlocos() {
    try {
        const blocoSelect = document.getElementById('bloco-select');
        if (!blocoSelect) return;
        blocoSelect.innerHTML = '<option value="">Selecione um Bloco</option>';

        const salasList = await fetchSalasFromApi();
        // Agrupa por bloco
        const blocosMap = {};
        salasList.forEach(s => {
            const bloco = s.bloco || 'Sem Bloco';
            blocosMap[bloco] = blocosMap[bloco] || [];
            blocosMap[bloco].push(s);
        });
        // Atualiza mockData em memória para compatibilidade com filtrarSalas
        mockData.length = 0;
        Object.keys(blocosMap).forEach(blocoName => {
            mockData.push({ bloco: blocoName, salas: blocosMap[blocoName] });
            const option = document.createElement('option');
            option.value = blocoName;
            option.textContent = blocoName;
            blocoSelect.appendChild(option);
        });
    } catch (e) {
        console.error('Erro ao popular Select de Blocos:', e);
    }
}

window.filtrarSalas = function(blocoName) {
    const salaSelect = document.getElementById('sala-select');
    // Limpa as opções existentes e adiciona o placeholder
    salaSelect.innerHTML = '<option value="">Selecione uma Sala</option>'; 
    const searchAlert = document.getElementById('search-alert');
    searchAlert.textContent = ''; 
    
    currentBlockCode = blocoName;
    currentSalaCode = '';
    currentSalaAgenda = [];
    
    // Desabilita por padrão
    salaSelect.disabled = true;

    if (blocoName) {
        const blocoEncontrado = mockData.find(b => b.bloco.toLowerCase() === blocoName.toLowerCase());

        if (blocoEncontrado) {
            blocoEncontrado.salas.forEach(sala => {
                const option = document.createElement('option');
                option.value = sala.codigo; 
                option.textContent = `${sala.codigo} - ${sala.nome}`; 
                salaSelect.appendChild(option);
            });
            
            // Habilita o campo Sala nativo
            salaSelect.disabled = false; 

        }
        // Limpa o calendário se o bloco for selecionado, mas não a sala
        initializeFullCalendar([]); 
    } else {
        // Bloco deselecionado: reseta
        initInitialDetails();
    }
};

/**
 * Busca os detalhes da sala e ATUALIZA O FULLCALENDAR.
 */
window.buscarCalendario = async function(blocoCode, salaCode) {
    const searchAlert = document.getElementById('search-alert');
    searchAlert.textContent = '';
    currentBlockCode = blocoCode;
    currentSalaCode = salaCode;
    currentSalaAgenda = [];

    if (!salaCode || !blocoCode) {
        searchAlert.textContent = "Por favor, selecione um Bloco e uma Sala.";
        initializeFullCalendar([]);
        return;
    }

    // Tenta buscar agenda específica do backend
    try {
        const res = await fetch(`/salas/busca/codigo/${encodeURIComponent(salaCode)}/agenda`);
        if (!res.ok) throw new Error('HTTP ' + res.status);
        const eventos = await res.json(); // espera array compatível com FullCalendar
        currentSalaAgenda = eventos;
        const salaEncontrada = mockData.flatMap(b => b.salas).find(s => s.codigo.toLowerCase() === salaCode.toLowerCase());
        searchAlert.innerHTML = `Exibindo agenda para: <strong>${salaEncontrada?.codigo || salaCode} - ${salaEncontrada?.nome || ''}</strong> (Capacidade: ${salaEncontrada?.capacidade || 'N/A'})`;
        initializeFullCalendar(currentSalaAgenda);
        return;
    } catch (e) {
        console.warn('Erro ao buscar agenda do backend, fallback para dados locais:', e);
    }

    // Fallback: busca no mockData (existente)
    const bloco = mockData.find(b => b.bloco.toLowerCase() === blocoCode.toLowerCase());
    const salaEncontrada = bloco?.salas.find(s => s.codigo.toLowerCase() === salaCode.toLowerCase());
    if (!salaEncontrada) {
        searchAlert.textContent = "Sala não encontrada.";
        initializeFullCalendar([]);
        return;
    }
    currentSalaAgenda = salaEncontrada.agenda || [];
    searchAlert.innerHTML = `Exibindo agenda para: <strong>${salaEncontrada.codigo} - ${salaEncontrada.nome}</strong> (Capacidade: ${salaEncontrada.capacidade})`;
    initializeFullCalendar(currentSalaAgenda);
};
// =========================================================================
// INICIALIZAÇÃO DA APLICAÇÃO
// =========================================================================

/**
 * Define o estado inicial dos campos de detalhe da sala e desenha o calendário vazio.
 */
function initInitialDetails() {
    currentBlockCode = '';
    currentSalaCode = '';
    currentSalaAgenda = [];
    
    document.getElementById('search-alert').textContent = 'Selecione um bloco e uma sala para visualizar a agenda.';

    // Inicializa o calendário com uma agenda vazia
    initializeFullCalendar([]);
}

function applyTheme(theme) {
    const body = document.body;
    const iconEl = document.getElementById('theme-icon');
    if (!body || !iconEl) return;

    if (theme === 'dark') {
        body.classList.add('dark-theme');
        iconEl.textContent = '☀'; // ao ativar dark, mostrar sol para indicar voltar ao claro
    } else {
        body.classList.remove('dark-theme');
        iconEl.textContent = '🌙';
    }
    try { localStorage.setItem('app-theme', theme); } catch (e) {}
}

function toggleTheme() {
    const current = localStorage.getItem('app-theme') === 'dark' ? 'dark' : 'light';
    const next = current === 'dark' ? 'light' : 'dark';
    applyTheme(next);
}

function applyThemeFromStorage() {
    const saved = localStorage.getItem('app-theme') === 'dark' ? 'dark' : 'light';
    applyTheme(saved);
}

function initApp() {
    // 1. Preenche o dropdown de Blocos/Setores
    popularSelectBlocos();
    
    // 2. Define o estado inicial da visualização
    initInitialDetails();

    // 3. Aplica tema salvo e adiciona listener do botão (se existir)
    applyThemeFromStorage();
    const themeBtn = document.getElementById('theme-toggle-btn');
    if (themeBtn) themeBtn.addEventListener('click', toggleTheme);
}

// Garante que a inicialização só ocorra depois que o DOM e todas as bibliotecas estiverem carregados
$(document).ready(initApp);