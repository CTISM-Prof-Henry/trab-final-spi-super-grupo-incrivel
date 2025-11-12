function funcao_clique() {
    window.alert('Oi!');
}

// Código para visualizar e ocultar senha 
document.addEventListener('DOMContentLoaded', function() {
    const togglePasswordBtn = document.getElementById('togglePassword');
    if (togglePasswordBtn) {
        togglePasswordBtn.addEventListener('click', function() {
            const passwordField = document.getElementById('password');
            const eyeIcon = document.getElementById('eyeIcon');
            if (!passwordField || !eyeIcon) return;
            
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                eyeIcon.classList.remove('fa-eye');
                eyeIcon.classList.add('fa-eye-slash');
            } else {
                passwordField.type = 'password';
                eyeIcon.classList.remove('fa-eye-slash');
                eyeIcon.classList.add('fa-eye');
            }
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const themeBtn = document.getElementById('toggleTheme');
    if (!themeBtn) return;

    themeBtn.addEventListener('click', function() {
        const isDark = !document.body.classList.contains('bg-dark');

        // Body
        if (isDark) {
            document.body.classList.add('bg-dark', 'text-white');
        } else {
            document.body.classList.remove('bg-dark', 'text-white');
        }

        // Navbar
        const navbar = document.querySelector('.navbar');
        if (navbar) {
            if (isDark) {
                navbar.classList.add('bg-dark', 'navbar-dark');
                navbar.classList.remove('bg-light');
            } else {
                navbar.classList.remove('bg-dark', 'navbar-dark');
                navbar.classList.add('bg-light');
            }
        }

        // Card de login com bg-white
        document.querySelectorAll('.bg-white').forEach(function(el) {
            if (isDark) {
                el.classList.add('bg-dark', 'text-white');
                el.classList.remove('bg-white');
            } else {
                el.classList.remove('bg-dark', 'text-white');
                el.classList.add('bg-white');
            }
        });

        // Card de login com cinza
        document.querySelectorAll('.cinza').forEach(function(el) {
            if (document.body.classList.contains('bg-dark')) {
                el.classList.remove('cinza');
                el.classList.add('bg-dark', 'text-white');
            } else {
                el.classList.remove('bg-dark', 'text-white');
                el.classList.add('cinza');
            }
        });

        // Links
        document.querySelectorAll('.tiraEfeitoLink').forEach(function(el){
            if (isDark) {
                el.classList.add('text-white');
                el.classList.remove('text-dark');
            } else {
                el.classList.remove('text-white');
                el.classList.add('text-dark');
            }
        });

        // Botão de tema
        const themeBtnLocal = document.getElementById('toggleTheme');
        if (themeBtnLocal) {
            if (isDark) {
                themeBtnLocal.classList.add('bg-dark', 'text-white');
                themeBtnLocal.classList.remove('cinza', 'btn-outline-dark');
                themeBtnLocal.style.borderColor = '#fff';
            } else {
                themeBtnLocal.classList.remove('bg-dark', 'text-white');
                themeBtnLocal.classList.add('cinza', 'btn-outline-dark');
                themeBtnLocal.style.borderColor = '';
            }
        }
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('loginForm');
    if (!form) return;

    form.addEventListener('submit', function (ev) {
        ev.preventDefault();
        const usuario = document.getElementById('usuario')?.value?.trim() || '';
        const remember = document.getElementById('remember')?.checked;

        if (!usuario) {
            alert('Informe o nome de usuário.');
            return;
        }

        if (remember) {
            localStorage.setItem('username', usuario);
        } else {
            sessionStorage.setItem('username', usuario);
        }

        // ajustar caso seu context-path seja diferente
        const APP_BASE = '/sistema_agendamento_poli';
        window.location.href = `${APP_BASE}/geral/pagina-inicial.html`;
    });
});