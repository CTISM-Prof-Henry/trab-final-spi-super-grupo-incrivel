document.addEventListener('DOMContentLoaded', (event) => {

    let notifications = [
        // IDs corrigidos para serem únicos:
        { id: 1, date: "05/10/2025", title: "Pedido de agendamento da Sala G208", description: "Sala agendada com sucesso. Este é o conteúdo completo da notificação :).", isRead: false },
        { id: 2, date: "04/10/2025", title: "Pedido de agendamento da Sala G208", description: "Pedido em análise.", isRead: true },
        { id: 3, date: "03/10/2025", title: "Pedido de agendamento da Sala F109", description: "A sala agendada não está mais disponível. Por favor, tente outra data ou sala.", isRead: false },
        { id: 4, date: "03/10/2025", title: "Pedido de agendamento da Sala F109", description: "Sala agendada com sucesso.", isRead: false },
    ];

    // Elementos da Tabela e Ações
    const notificationBody = document.getElementById('notificationBody');
    const markAllReadBtn = document.getElementById('markAllRead');
    const clearAllBtn = document.getElementById('clearAll');

    // Elementos do Modal de Exclusão (Delete Modal)
    const deleteModal = document.getElementById('deleteModal');
    const confirmDeleteBtn = document.getElementById('confirmDelete');
    const cancelDeleteBtn = document.getElementById('cancelDelete');
    let notificationToDeleteId = null;

    // Elementos do Modal de Visualização (View Modal) - NOVOS
    const viewModal = document.getElementById('viewModal');
    const viewTitle = document.getElementById('viewTitle'); 
    const viewDate = document.getElementById('viewDate');   
    const viewDescription = document.getElementById('viewDescription'); 
    const closeViewModalBtn = document.getElementById('closeViewModal'); 
    const closeButtonX = document.getElementById('closeButtonX'); 

    // ----------------------------------------------------
    // 1. Função de Renderização (Atualizada para cliques)
    // ----------------------------------------------------
    function renderNotifications() {
        notificationBody.innerHTML = ''; 

        if (notifications.length === 0) {
            notificationBody.innerHTML = '<tr><td colspan="4" style="text-align:center;">Não há notificações.</td></tr>';
            return;
        }

        notifications.forEach(notif => {
            const row = notificationBody.insertRow();
            row.classList.toggle('unread', !notif.isRead); 
            
            //  Adiciona um evento de clique em TODA A LINHA para abrir a visualização
            row.onclick = () => openViewModal(notif.id, row);
            
            row.insertCell().textContent = notif.date;
            row.insertCell().textContent = notif.title;
            // Usamos substring aqui para garantir que o texto não seja muito longo na lista
            row.insertCell().textContent = notif.description.substring(0, 50) + '...'; 

            // Célula da Ação (Exclusão)
            const actionCell = row.insertCell();
            const deleteButton = document.createElement('button');
            deleteButton.classList.add('delete-btn');
            deleteButton.innerHTML = '<i class="fas fa-trash-alt"></i>';
            deleteButton.setAttribute('data-id', notif.id);
            
            //  Impedir que o clique na lixeira abra o modal de visualização da linha
            deleteButton.onclick = (e) => {
                e.stopPropagation(); // Essencial para evitar a abertura do View Modal
                openDeleteModal(e);
            }
            actionCell.appendChild(deleteButton);
        });
    }

    // ----------------------------------------------------
    // 2. Lógica do Modal de Visualização (NOVO)
    // ----------------------------------------------------
    function openViewModal(id, rowElement) {
        const notif = notifications.find(n => n.id === id);
        if (!notif) return;

        // 1. Preenche o Modal com o conteúdo completo
        viewTitle.textContent = notif.title;
        viewDate.textContent = `Data: ${notif.date}`;
        viewDescription.textContent = notif.description;

        // 2. Marca a notificação como lida e remove a cor de destaque
        if (!notif.isRead) {
            notif.isRead = true;
            rowElement.classList.remove('unread');
        }

        // 3. Exibe o modal
        viewModal.style.display = 'block';
    }

    // Função para fechar o modal de visualização
    function closeViewModal() {
        viewModal.style.display = 'none';
    }

    // Atribui os eventos de fechar do modal de visualização
    if (closeViewModalBtn) closeViewModalBtn.onclick = closeViewModal;
    if (closeButtonX) closeButtonX.onclick = closeViewModal; 

    // ----------------------------------------------------
    // 3. Lógica do Modal de Exclusão (EXISTENTE)
    // ----------------------------------------------------
    function openDeleteModal(event) {
        notificationToDeleteId = parseInt(event.currentTarget.getAttribute('data-id'));
        deleteModal.style.display = 'block';
    }

    function closeDeleteModal() {
        deleteModal.style.display = 'none';
        notificationToDeleteId = null;
    }

    if (confirmDeleteBtn) {
        confirmDeleteBtn.onclick = () => {
            if (notificationToDeleteId !== null) {
                // Filtra a lista, removendo a notificação com o ID correspondente
                notifications = notifications.filter(notif => notif.id !== notificationToDeleteId);
                renderNotifications();
            }
            closeDeleteModal();
        };
    }
    
    if (cancelDeleteBtn) cancelDeleteBtn.onclick = closeDeleteModal;

    // ----------------------------------------------------
    // 4. Lógica dos Botões de Ação e Eventos Globais
    // ----------------------------------------------------
    if (markAllReadBtn) {
        markAllReadBtn.onclick = () => {
            notifications = notifications.map(notif => ({ ...notif, isRead: true }));
            renderNotifications();
            alert('Todas as notificações foram marcadas como lidas!');
        };
    }

    if (clearAllBtn) {
        clearAllBtn.onclick = () => {
            if (confirm('Tem certeza que deseja limpar todas as notificações?')) {
                notifications = [];
                renderNotifications();
                alert('Todas as notificações foram removidas!');
            }
        };
    }

    // Fecha qualquer modal se o usuário clicar fora dele
    window.onclick = (event) => {
        if (event.target == deleteModal) {
            closeDeleteModal();
        }
        if (event.target == viewModal) {
            closeViewModal();
        }
    };

    // ----------------------------
    // THEME TOGGLE (Dark Mode)
    // ----------------------------
    function applyTheme(theme) {
        const body = document.body;
        const iconEl = document.getElementById('theme-icon');
        if (!body || !iconEl) return;

        if (theme === 'dark') {
            body.classList.add('dark-theme');
            iconEl.textContent = '☀';
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

    // Aplica tema salvo e associa botão (quando o DOM estiver pronto)
    applyThemeFromStorage();
    const themeBtn = document.getElementById('theme-toggle-btn');
    if (themeBtn) themeBtn.addEventListener('click', toggleTheme);

    // Inicializa a renderização
    renderNotifications();
});