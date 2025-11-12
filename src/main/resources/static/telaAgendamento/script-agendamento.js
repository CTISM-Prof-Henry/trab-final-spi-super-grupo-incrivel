document.addEventListener('DOMContentLoaded', function() {
    // toggle tema existente
    const toggle = document.getElementById('toggleTheme');
    if (toggle) {
        toggle.addEventListener('click', function() {
            document.body.classList.toggle('bg-dark');
            document.body.classList.toggle('text-white');

            // Navbar
            const navbar = document.querySelector('.navbar');
            navbar.classList.toggle('bg-dark');
            navbar.classList.toggle('navbar-dark');
            navbar.classList.toggle('bg-azul');
            if (navbar.classList.contains('bg-dark')) {
                navbar.style.borderBottom = '2px solid #fff';
            } else {
                navbar.style.borderBottom = '';
            }

            // Quadrados
            document.querySelectorAll('.bg-white').forEach(function(el) {
                el.classList.toggle('bg-dark');
                el.classList.toggle('text-white');
                el.classList.toggle('bg-white');
            });

            // Títulos com bg-azul
            document.querySelectorAll('.bg-azul, .bg-dark.filtro-azul').forEach(function(el) {
                if (document.body.classList.contains('bg-dark')) {
                    el.classList.remove('bg-azul');
                    el.classList.add('bg-dark', 'filtro-azul');
                } else {
                    el.classList.remove('bg-dark', 'filtro-azul');
                    el.classList.add('bg-azul');
                }
            });

            // Tabela
            document.querySelectorAll('.table').forEach(function(el) {
                el.classList.toggle('table-dark');
            });

            // Links
            document.querySelectorAll('.tiraEfeitoLink').forEach(function(el){
                el.classList.toggle('text-dark');
                el.classList.toggle('text-white');
            });
        });
    }

    // === integração com backend ===
    const APP_BASE = '/sistema_agendamento_poli';
    const salaSelect = document.getElementById('sala');
    const blocoSelect = document.getElementById('bloco');
    const form = document.getElementById('agendaForm');
    const agendasBody = document.getElementById('agendasBody');
    const cancelBtn = document.getElementById('cancelBtn');

    // lê usuário salvo via init-user.js (window.getCurrentUser)
    function getStoredUser() {
        if (window.getCurrentUser) return window.getCurrentUser();
        const nome = sessionStorage.getItem('username') || localStorage.getItem('username') || null;
        const idRaw = sessionStorage.getItem('userId') || localStorage.getItem('userId') || null;
        return { id: idRaw ? Number(idRaw) : null, username: nome };
    }

    async function ensureUserId() {
        const u = getStoredUser();
        if (u && u.id) return u.id;
        const username = u?.username;
        // se existir username, tenta recuperar pelo backend
        if (username) {
            try {
                const resp = await fetch(`${APP_BASE}/usuarios/busca/identificador/${encodeURIComponent(username)}`);
                if (resp.ok) {
                    const usuarioObj = await resp.json();
                    const id = usuarioObj?.id ?? (Array.isArray(usuarioObj) && usuarioObj[0]?.id) ?? null;
                    if (id && window.setCurrentUser) {
                        window.setCurrentUser({ id: id, username: username }, !!localStorage.getItem('username'));
                    } else if (id) {
                        sessionStorage.setItem('userId', String(id));
                    }
                    return id;
                }
            } catch (err) {
                console.error('Erro ao buscar userId por username:', err);
            }
        }

        // fallback: cria um usuário "anônimo" no backend e retorna o id
        try {
            const anonIdent = 'usuario_anonimo';
            const dto = {
                nome: 'Usuário Anônimo',
                telefone: '',
                email: `anon@local`,
                identificador: anonIdent,
                senha: ''
            };
            const createResp = await fetch(`${APP_BASE}/usuarios`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dto)
            });

            // tenta obter usuário criado (pode retornar body JSON ou apenas Location)
            if (createResp.ok) {
                // se body JSON
                if (createResp.headers.get('Content-Type')?.includes('application/json')) {
                    const created = await createResp.json();
                    const id = created?.id ?? null;
                    if (id) {
                        sessionStorage.setItem('userId', String(id));
                        sessionStorage.setItem('username', 'Usuário Anônimo');
                        return id;
                    }
                }
                // se Location presente
                const loc = createResp.headers.get('Location');
                if (loc) {
                    const url = loc.startsWith('http') ? loc : `${APP_BASE}${loc.startsWith('/') ? '' : '/'}${loc}`;
                    const getResp = await fetch(url);
                    if (getResp.ok) {
                        const created = await getResp.json();
                        const id = created?.id ?? null;
                        if (id) {
                            sessionStorage.setItem('userId', String(id));
                            sessionStorage.setItem('username', 'Usuário Anônimo');
                            return id;
                        }
                    }
                }
            } else {
                // se criação falhar (p.ex. por duplicidade), tentar buscar o usuário anônimo existente
                const lookup = await fetch(`${APP_BASE}/usuarios/busca/identificador/${encodeURIComponent('usuario_anonimo')}`);
                if (lookup.ok) {
                    const obj = await lookup.json();
                    const id = obj?.id ?? (Array.isArray(obj) && obj[0]?.id) ?? null;
                    if (id) {
                        sessionStorage.setItem('userId', String(id));
                        sessionStorage.setItem('username', 'Usuário Anônimo');
                        return id;
                    }
                }
            }
        } catch (e) {
            console.error('Erro ao criar usuário anônimo:', e);
        }

        return null;
    }

    // carrega opções de salas (/salas/listar)
    async function carregarSalas() {
        if (!salaSelect) return;
        try {
            const resp = await fetch(`${APP_BASE}/salas/listar`);
            if (!resp.ok) throw new Error('Falha ao listar salas: ' + resp.status);
            const salas = await resp.json();
            salaSelect.innerHTML = '<option value="">Selecione uma sala</option>';
            salas.forEach(s => {
                const id = s.id ?? s.codigo ?? s.nome;
                const nome = s.nome ?? s.codigo ?? `Sala ${id}`;
                const opt = document.createElement('option');
                opt.value = id;
                opt.textContent = nome;
                salaSelect.appendChild(opt);
            });
            salaSelect.disabled = false;
        } catch (err) {
            console.error('Erro ao carregar salas:', err);
            salaSelect.innerHTML = '<option value="">Erro ao carregar salas</option>';
            salaSelect.disabled = true;
        }
    }

    // carrega agendamentos (/agendamento/listar)
    async function carregarAgendamentos() {
        if (!agendasBody) return;
        try {
            const resp = await fetch(`${APP_BASE}/agendamento/listar`);
            if (!resp.ok) throw new Error('Falha ao listar agendamentos');
            const agendamentos = await resp.json();
            agendasBody.innerHTML = '';
            agendamentos.forEach(a => {
                const tr = document.createElement('tr');
                const data = formatarData(a.data || a.dia || a.dataAgenda);
                const de = a.horarioInicio ?? a.horaInicio ?? a.de ?? a.inicio ?? '';
                const ate = a.horarioFim ?? a.horaFim ?? a.ate ?? a.fim ?? '';
                const salaNome = (a.sala && (a.sala.nome || a.sala.codigo)) || a.sala || a.salaId || '';
                const bloco = a.bloco ?? a.localBloco ?? '';
                const usuario = a.usuarioNome ?? a.usuario ?? a.criadoPor ?? '';
                tr.innerHTML = `
                    <td>${data}</td>
                    <td>${escapeHtml(de)}</td>
                    <td>${escapeHtml(ate)}</td>
                    <td>${escapeHtml(salaNome)}</td>
                    <td>${escapeHtml(bloco)}</td>
                    <td>${escapeHtml(usuario)}</td>
                    <td>
                        <button class="btn btn-sm btn-danger btn-delete" data-id="${a.id}">Excluir</button>
                    </td>
                `;
                agendasBody.appendChild(tr);
            });

            document.querySelectorAll('.btn-delete').forEach(btn => {
                btn.addEventListener('click', async () => {
                    const id = btn.dataset.id;
                    if (!confirm('Deseja realmente excluir este agendamento?')) return;
                    try {
                        const resp = await fetch(`${APP_BASE}/agendamento/${id}`, { method: 'DELETE' });
                        if (!resp.ok) throw new Error('Falha ao deletar');
                        await carregarAgendamentos();
                    } catch (err) {
                        console.error('Erro ao deletar agendamento:', err);
                        alert('Erro ao deletar agendamento');
                    }
                });
            });

        } catch (err) {
            console.error('Erro ao carregar agendamentos:', err);
        }
    }

    // envio do formulário -> cria agendamento (POST /agendamento)
    if (form) {
        form.addEventListener('submit', async (ev) => {
            ev.preventDefault();
            const salaValue = salaSelect?.value || '';
            const blocoValue = blocoSelect?.value || '';
            const dataValue = document.getElementById('dias')?.value || '';
            const deValue = document.getElementById('de')?.value || '';
            const ateValue = document.getElementById('ate')?.value || '';

            if (!salaValue || !dataValue || !deValue || !ateValue) {
                alert('Preencha sala, dia, horário de início e fim.');
                return;
            }

            // garante userId antes de enviar
            const usuarioId = await ensureUserId();
            if (!usuarioId) {
                alert('ID do usuário não disponível. Faça login para prosseguir.');
                return;
            }

            const dto = {
                salaId: Number(salaValue),
                usuarioId: Number(usuarioId),
                data: dataValue,               // "YYYY-MM-DD" -> LocalDate
                horarioInicio: deValue,        // "HH:mm" -> LocalTime
                horarioFim: ateValue,          // "HH:mm" -> LocalTime
                status: 'PENDENTE'
            };

            try {
                const resp = await fetch(`${APP_BASE}/agendamento`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(dto)
                });
                if (!resp.ok) {
                    const text = await resp.text();
                    throw new Error(text || 'Erro ao criar agendamento');
                }
                form.reset();
                await carregarAgendamentos();
                alert('Agendamento criado com sucesso.');
            } catch (err) {
                console.error('Erro ao criar agendamento:', err);
                alert('Erro ao criar agendamento. Verifique console.');
            }
        });
    }

    if (cancelBtn) {
        cancelBtn.addEventListener('click', () => {
            form && form.reset();
        });
    }

    // utilitários
    function formatarData(dateStr) {
        if (!dateStr) return '';
        try {
            const d = new Date(dateStr);
            if (isNaN(d)) return dateStr;
            return d.toLocaleDateString('pt-BR');
        } catch {
            return dateStr;
        }
    }

    function escapeHtml(text) {
        if (!text) return '';
        return String(text)
            .replaceAll('&', '&amp;')
            .replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;')
            .replaceAll('"', '&quot;')
            .replaceAll("'", '&#039;');
    }

    // inicializa
    carregarSalas();
    carregarAgendamentos();
});
