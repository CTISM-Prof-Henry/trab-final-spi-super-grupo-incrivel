// --- SEU CÓDIGO ANTIGO (tema escuro) ---
document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("toggleTheme").addEventListener("click", function () {
    document.body.classList.toggle("bg-dark");
    document.body.classList.toggle("text-white");
    const navbar = document.querySelector(".navbar");
    navbar.classList.toggle("bg-dark");
    navbar.classList.toggle("navbar-dark");
    navbar.classList.toggle("bg-azul");
    if (navbar.classList.contains("bg-dark")) {
      navbar.style.borderBottom = "1px solid #fff";
    } else {
      navbar.style.borderBottom = "";
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
});

// --- NOVO CÓDIGO (integração com backend) ---
document.addEventListener("DOMContentLoaded", function () {
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

  async function carregarNotificacoes() {
    try {
      const resposta = await fetch("/notificacoes/listar");
      const notificacoes = await resposta.json();

      tbody.innerHTML = "";

      notificacoes.forEach((n) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${formatarData(n.data)}</td>
          <td>${n.titulo}</td>
          <td>${n.descricao}</td>
          <td>
            <button class="viewBtn" data-id="${n.id}"><i class="fas fa-eye"></i></button>
            <button class="deleteBtn" data-id="${n.id}"><i class="fas fa-trash"></i></button>
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
        const resposta = await fetch(`/notificacoes/busca/id/${id}`);
        const notificacao = await resposta.json();

        viewTitle.textContent = notificacao.titulo;
        viewDate.textContent = formatarData(notificacao.data);
        viewDescription.textContent = notificacao.descricao;
        viewModal.style.display = "block";
      });
    });

    document.querySelectorAll(".deleteBtn").forEach((btn) => {
      btn.addEventListener("click", () => {
        notificationToDelete = btn.dataset.id;
        modalDelete.style.display = "block";
      });
    });
  }

  confirmDeleteBtn.addEventListener("click", async () => {
    if (notificationToDelete) {
      await fetch(`/notificacoes/${notificationToDelete}`, { method: "DELETE" });
      notificationToDelete = null;
      modalDelete.style.display = "none";
      carregarNotificacoes();
    }
  });

  cancelDeleteBtn.addEventListener("click", () => {
    modalDelete.style.display = "none";
  });

  closeViewModal.addEventListener("click", () => (viewModal.style.display = "none"));
  closeButtonX.addEventListener("click", () => (viewModal.style.display = "none"));

  function formatarData(dataString) {
    if (!dataString) return "";
    const data = new Date(dataString);
    return data.toLocaleDateString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  }

  document.getElementById("markAllRead").addEventListener("click", async () => {
    const resposta = await fetch("/notificacoes/listar");
    const notificacoes = await resposta.json();
    for (const n of notificacoes) {
      await fetch(`/notificacoes/${n.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...n, lida: true }),
      });
    }
    carregarNotificacoes();
  });

  document.getElementById("clearAll").addEventListener("click", async () => {
    const resposta = await fetch("/notificacoes/listar");
    const notificacoes = await resposta.json();
    for (const n of notificacoes) {
      await fetch(`/notificacoes/${n.id}`, { method: "DELETE" });
    }
    carregarNotificacoes();
  });

  carregarNotificacoes();
});
