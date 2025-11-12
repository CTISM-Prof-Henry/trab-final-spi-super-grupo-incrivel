document.addEventListener('DOMContentLoaded', function () {
  const username = sessionStorage.getItem('username') || localStorage.getItem('username') || null;
  const userIdRaw = sessionStorage.getItem('userId') || localStorage.getItem('userId') || null;
  const userId = userIdRaw ? Number(userIdRaw) : null;

  window.currentUser = { id: userId, username: username };
  window.getCurrentUser = () => window.currentUser;

  window.setCurrentUser = function (user, remember = false) {
    if (!user) return;
    const name = user.username ?? user.nome ?? user.name ?? '';
    const id = user.id ?? user.userId ?? null;
    if (remember) {
      localStorage.setItem('username', name);
      if (id !== null) localStorage.setItem('userId', String(id));
    } else {
      sessionStorage.setItem('username', name);
      if (id !== null) sessionStorage.setItem('userId', String(id));
    }
    window.currentUser = { id: id !== null ? Number(id) : null, username: name };
    const welcome = document.getElementById('welcome-message');
    if (welcome) welcome.textContent = name ? `Olá, ${name}` : 'Olá, Usuário';
  };

  window.clearCurrentUser = function () {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('userId');
    localStorage.removeItem('username');
    localStorage.removeItem('userId');
    window.currentUser = { id: null, username: null };
    const welcome = document.getElementById('welcome-message');
    if (welcome) welcome.textContent = 'Olá, Usuário';
  };

  // atualiza saudação se existir
  const welcome = document.getElementById('welcome-message');
  if (welcome) {
    welcome.textContent = username ? `Olá, ${username}` : 'Olá, Usuário';
  }
});