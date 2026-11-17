document.addEventListener('DOMContentLoaded', function() {
    // Obtener el botón de cambio de tema
    const themeToggle = document.getElementById('theme-toggle');

    // Comprobar si hay una preferencia guardada
    const currentTheme = localStorage.getItem('theme');

    // Si hay una preferencia guardada, aplicarla
    if (currentTheme) {
        document.body.classList.add(currentTheme);

        // Actualizar la apariencia del botón
        if (currentTheme === 'dark-mode') {
            document.body.classList.add('dark-mode');
        }
    }

    // Añadir evento de clic al botón
    themeToggle.addEventListener('click', function() {
        // Alternar la clase dark-mode en el body
        document.body.classList.toggle('dark-mode');

        // Guardar la preferencia en localStorage
        if (document.body.classList.contains('dark-mode')) {
            localStorage.setItem('theme', 'dark-mode');
        } else {
            localStorage.setItem('theme', '');
        }
    });
});

