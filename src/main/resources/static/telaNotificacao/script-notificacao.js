//
document.addEventListener("DOMContentLoaded", function () {
  // lê username salvo no login (sessionStorage primeiro, fallback para localStorage)
  const savedUser = sessionStorage.getItem('username') || localStorage.getItem('username');
  if (savedUser) {
    const welcome = document.getElementById('welcome-message');
    if (welcome) welcome.textContent = `Olá, ${savedUser}`;
  }

  // --- TOGGLE TEMA (ajustado ao HTML) ---
  const themeToggleBtn = document.getElementById("theme-toggle-btn");
  const navbar = document.getElementById("main-navbar");

  // novo: elementos para badge de não-lidas
  const notificationBtn = document.querySelector(".notification-btn");
  const unreadBadge = document.getElementById("unreadCount");

  if (themeToggleBtn) {
    themeToggleBtn.addEventListener("click", function () {
      document.body.classList.toggle("bg-dark");
      document.body.classList.toggle("text-white");

      if (navbar) {
        navbar.classList.toggle("bg-dark");
        navbar.classList.toggle("navbar-dark");
        navbar.classList.toggle("bg-azul");
        if (navbar.classList.contains("bg-dark")) {
          navbar.style.borderBottom = "1px solid #fff";
        } else {
          navbar.style.borderBottom = "";
        }
      }

      document.querySelectorAll(".bg-azul").forEach(function (el) {
        el.classList.toggle("bg-dark");
        el.classList.toggle("bg-azul");
      });
      document.querySelectorAll(".bg-white").forEach(function (el) {
        el.classList.toggle("bg-dark");
        el.classList.toggle("text-white");
        el.classList.toggle("bg-white");
      });
      document.querySelectorAll(".table").forEach(function (el) {
        el.classList.toggle("table-dark");
      });
      document.querySelectorAll(".tiraEfeitoLink").forEach(function (el) {
        el.classList.toggle("text-dark");
        el.classList.toggle("text-white");
      });
    });
  }

  // --- INTEGRAÇÃO COM BACKEND ---
  const tbody = document.getElementById("notificationBody");
  const modalDelete = document.getElementById("deleteModal");
  const confirmDeleteBtn = document.getElementById("confirmDelete");
  const cancelDeleteBtn = document.getElementById("cancelDelete");
  const viewModal = document.getElementById("viewModal");
  const viewTitle = document.getElementById("viewTitle");
  const viewDate = document.getElementById("viewDate");
  const viewDescription = document.getElementById("viewDescription");
  const closeViewModal = document.getElementById("closeViewModal");
  const closeButtonX = document.getElementById("closeButtonX");

  let notificationToDelete = null;

  // atualiza badge de não-lidas
  function atualizarBadge(notificacoes) {
    if (!unreadBadge) return;
    const naoLidas = notificacoes.filter((n) => !n.lida).length;
    if (naoLidas > 0) {
      unreadBadge.textContent = naoLidas;
      unreadBadge.style.display = "inline-block";
    } else {
      unreadBadge.textContent = "";
      unreadBadge.style.display = "none";
    }
  }

  async function carregarNotificacoes() {
    try {
      if (!tbody) return;
      const resposta = await fetch("/notificacoes/listar", { headers: { "Accept": "application/json" } });
      if (!resposta.ok) throw new Error("Resposta inválida do servidor");
      const notificacoes = await resposta.json();

      // atualiza badge
      atualizarBadge(notificacoes);

      tbody.innerHTML = "";

      notificacoes.forEach((n) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${formatarData(n.data)}</td>
          <td>${escapeHtml(n.titulo)}</td>
          <td>${escapeHtml(n.descricao)}</td>
          <td>
            <button class="viewBtn" data-id="${n.id}" title="Visualizar"><i class="fas fa-eye"></i></button>
            <button class="deleteBtn" data-id="${n.id}" title="Excluir"><i class="fas fa-trash"></i></button>
          </td>
        `;
        tbody.appendChild(tr);
      });

      adicionarEventosBotoes();
    } catch (erro) {
      console.error("Erro ao carregar notificações:", erro);
    }
  }

  function adicionarEventosBotoes() {
    document.querySelectorAll(".viewBtn").forEach((btn) => {
      btn.addEventListener("click", async () => {
        const id = btn.dataset.id;
        try {
          const resposta = await fetch(`/notificacoes/busca/id/${id}`, { headers: { "Accept": "application/json" } });
          if (!resposta.ok) throw new Error("Notificação não encontrada");
          const notificacao = await resposta.json();

          viewTitle.textContent = notificacao.titulo || "";
          viewDate.textContent = formatarData(notificacao.data);
          viewDescription.textContent = notificacao.descricao || "";
          viewModal.style.display = "block";
        } catch (err) {
          console.error("Erro ao buscar notificação:", err);
        }
      });
    });

    document.querySelectorAll(".deleteBtn").forEach((btn) => {
      btn.addEventListener("click", () => {
        notificationToDelete = btn.dataset.id;
        if (modalDelete) modalDelete.style.display = "block";
      });
    });
  }

  if (confirmDeleteBtn) {
    confirmDeleteBtn.addEventListener("click", async () => {
      if (notificationToDelete) {
        try {
          const resp = await fetch(`/notificacoes/${notificationToDelete}`, { method: "DELETE" });
          if (!resp.ok) throw new Error("Falha ao deletar");
          notificationToDelete = null;
          if (modalDelete) modalDelete.style.display = "none";
          await carregarNotificacoes();
        } catch (err) {
          console.error("Erro ao deletar notificação:", err);
        }
      }
    });
  }

  if (cancelDeleteBtn) {
    cancelDeleteBtn.addEventListener("click", () => {
      if (modalDelete) modalDelete.style.display = "none";
      notificationToDelete = null;
    });
  }

  if (closeViewModal) closeViewModal.addEventListener("click", () => (viewModal.style.display = "none"));
  if (closeButtonX) closeButtonX.addEventListener("click", () => (viewModal.style.display = "none"));

  function formatarData(dataString) {
    if (!dataString) return "";
    const data = new Date(dataString);
    return data.toLocaleDateString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  }

  // simples escape para evitar injeção de HTML na renderização
  function escapeHtml(text) {
    if (!text) return "";
    return text
      .replaceAll("&", "&amp;")
      .replaceAll("<", "&lt;")
      .replaceAll(">", "&gt;")
      .replaceAll('"', "&quot;")
      .replaceAll("'", "&#039;");
  }

  // marcar todas como lidas (usa PUT por notificação)
  const markAllBtn = document.getElementById("markAllRead");
  if (markAllBtn) {
    markAllBtn.addEventListener("click", async () => {
      try {
        const resposta = await fetch("/notificacoes/listar");
        if (!resposta.ok) throw new Error("Falha ao listar");
        const notificacoes = await resposta.json();
        for (const n of notificacoes) {
          // envia apenas os campos necessários ao DTO conforme sua API
          const dto = { titulo: n.titulo, descricao: n.descricao, data: n.data, lida: true };
          await fetch(`/notificacoes/${n.id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto),
          });
        }
        await carregarNotificacoes();
      } catch (err) {
        console.error("Erro ao marcar todas como lidas:", err);
      }
    });
  }

  // limpar todas (DELETE por notificação)
  const clearAllBtn = document.getElementById("clearAll");
  if (clearAllBtn) {
    clearAllBtn.addEventListener("click", async () => {
      try {
        const resposta = await fetch("/notificacoes/listar");
        if (!resposta.ok) throw new Error("Falha ao listar");
        const notificacoes = await resposta.json();
        for (const n of notificacoes) {
          await fetch(`/notificacoes/${n.id}`, { method: "DELETE" });
        }
        await carregarNotificacoes();
      } catch (err) {
        console.error("Erro ao limpar notificações:", err);
      }
    });
  }

  // fecha modais ao clicar fora deles
  window.addEventListener("click", (e) => {
    if (e.target === modalDelete) modalDelete.style.display = "none";
    if (e.target === viewModal) viewModal.style.display = "none";
  });

  // carrega inicial
  carregarNotificacoes();
});
