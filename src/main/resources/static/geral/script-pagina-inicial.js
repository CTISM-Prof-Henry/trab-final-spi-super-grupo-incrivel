document.addEventListener('DOMContentLoaded', function() {
    const APP_BASE = '/sistema_agendamento_poli';

    // tema (mantive seu código)
    document.getElementById('toggleTheme').addEventListener('click', function() {
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

    // mostra nome salvo do login (se houver)
    const savedUser = sessionStorage.getItem('username') || localStorage.getItem('username');
    if (savedUser) {
        const welcome = document.getElementById('welcome-message');
        if (welcome) welcome.textContent = `Olá, ${savedUser}`;
    }

    // preencher tabela com agendamentos do backend
    async function carregarAgendamentos() {
        const tbody = document.getElementById('salasBody');
        if (!tbody) return;
        try {
            const resp = await fetch(`${APP_BASE}/agendamento/listar`, { headers: { 'Accept': 'application/json' } });
            if (!resp.ok) {
                console.error('Falha ao buscar agendamentos:', resp.status);
                return;
            }
            const agendamentos = await resp.json();
            tbody.innerHTML = '';
            (Array.isArray(agendamentos) ? agendamentos : []).forEach(a => {
                const sala = (a.sala && (a.sala.nome || a.sala.codigo)) || a.salaId || a.sala || '';
                const data = a.data ? formatarData(a.data) : '';
                const horarioInicio = a.horarioInicio ?? a.horaInicio ?? '';
                const horarioFim = a.horarioFim ?? a.horaFim ?? '';
                const descricao = a.descricao ?? a.observacao ?? a.descricaoAgenda ?? '';
                const usuario = (a.usuario && (a.usuario.nome || a.usuario.email)) || a.usuarioId || a.usuarioNome || '';
                const status = a.status ?? '';
                const row = `
                    <tr>
                        <td>${escapeHtml(sala)}</td>
                        <td>${escapeHtml(data)} ${horarioInicio ? '- ' + escapeHtml(horarioInicio) : ''} ${horarioFim ? ' até ' + escapeHtml(horarioFim) : ''}</td>
                        <td>${escapeHtml(descricao)}</td>
                        <td>${escapeHtml(usuario)}</td>
                        <td>${escapeHtml(status)}</td>
                    </tr>
                `;
                tbody.insertAdjacentHTML('beforeend', row);
            });
        } catch (err) {
            console.error('Erro ao carregar agendamentos:', err);
        }
    }

    function formatarData(dateStr) {
        if (!dateStr) return '';
        // aceita LocalDate (YYYY-MM-DD) ou ISO
        try {
            const d = new Date(dateStr);
            if (!isNaN(d)) return d.toLocaleDateString('pt-BR');
            return dateStr;
        } catch {
            return dateStr;
        }
    }

    function escapeHtml(text) {
        if (!text && text !== 0) return '';
        return String(text)
            .replaceAll('&', '&amp;')
            .replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;')
            .replaceAll('"', '&quot;')
            .replaceAll("'", '&#039;');
    }

    // inicializa
    carregarAgendamentos();

    // opcional: atualiza periodicamente
    // setInterval(carregarAgendamentos, 30_000);
});
