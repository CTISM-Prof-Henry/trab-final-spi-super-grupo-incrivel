document.addEventListener('DOMContentLoaded', function() {
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
});
