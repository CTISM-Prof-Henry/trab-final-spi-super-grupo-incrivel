document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("toggleTheme").addEventListener("click", function () {
    document.body.classList.toggle("bg-dark");
    document.body.classList.toggle("text-white");

    // parte do navbar
    const navbar = document.querySelector(".navbar");
    navbar.classList.toggle("bg-dark");
    navbar.classList.toggle("navbar-dark");
    navbar.classList.toggle("bg-azul");

    // coloca aquela linha branca em baixo do navbar. Topper.
    if (navbar.classList.contains("bg-dark")) {
      navbar.style.borderBottom = "1px solid #fff";
    } else {
      navbar.style.borderBottom = "";
    }

    // filtro
    document.querySelectorAll(".bg-azul").forEach(function (el) {
      el.classList.toggle("bg-dark");
      el.classList.toggle("bg-azul");
    });

    // quadrados
    document.querySelectorAll(".bg-white").forEach(function (el) {
      el.classList.toggle("bg-dark");
      el.classList.toggle("text-white");
      el.classList.toggle("bg-white");
    });
    // tabela
    document.querySelectorAll(".table").forEach(function (el) {
      el.classList.toggle("table-dark");
    });

    // Troca cor dos links em cima da tabela
    document.querySelectorAll(".tiraEfeitoLink").forEach(function (el) {
      el.classList.toggle("text-dark");
      el.classList.toggle("text-white");
    });
  });
});
