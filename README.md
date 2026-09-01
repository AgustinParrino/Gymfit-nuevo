<<<<<<< HEAD
# GymFit 1.0.0

Proyecto reorganizado como una aplicación Spring Boot Maven estándar. El frontend y el backend se ejecutan juntos en el puerto `8081`, por lo que no hace falta Live Server ni configurar CORS.

## Abrir en IntelliJ IDEA

1. Extraé el ZIP.
2. En IntelliJ elegí **File → Open**.
3. Seleccioná la carpeta que contiene este `pom.xml`.
4. Elegí **Open as Project** y esperá que Maven descargue las dependencias.
5. Verificá que el SDK del proyecto sea **Java 17** o superior.
6. Ejecutá `com.gymfit.GymFitApplication` con el botón verde.
7. Abrí [http://localhost:8081](http://localhost:8081).

También queda incluida la configuración de ejecución **GymFit** dentro de `.run`.

## Pantallas

- `/` — presentación y productos destacados.
- `/catalogo.html` — catálogo conectado a la base de datos.
- `/admin.html` — alta, edición y baja lógica de productos; stock y estados de pedidos.
- `/h2-console` — consola de la base local.
- `/api/estado` — comprobación rápida del backend.

## Base de datos sencilla para desarrollo

La aplicación usa H2 por defecto y guarda los datos en `./data/gymfit`. Esto permite ejecutarla desde IntelliJ sin instalar MySQL. Haciendo mas facil todo

Datos para `/h2-console`:

```text
JDBC URL: jdbc:h2:file:./data/gymfit;MODE=MySQL;AUTO_SERVER=TRUE
Usuario: sa
Contraseña: dejar vacía
```

## Usar MySQL

Creá la base `gymfit` y configurá estas variables de entorno en la ejecución de IntelliJ:

```text
DB_URL=jdbc:mysql://localhost:3306/gymfit?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true
DB_USER=root
DB_PASSWORD=tu_clave
```

No hay contraseñas reales guardadas en el código.

Si ya tenías una base vieja llamada `gymfit_pro`, no la reutilices directamente: la versión original tenía columnas obligatorias incompatibles con el formulario. Usá una base nueva llamada `gymfit` o realizá una migración antes de copiar información.

## Funcionalidades recuperadas y agregadas

- Estructura Maven convencional (`src/main/java`, `src/main/resources`, `src/test`).
- Catálogo dinámico desde la API.
- Alta, edición y baja lógica de productos.
- Control de stock.
- Registro o actualización del cliente por WhatsApp.
- Creación de pedidos con total calculado exclusivamente por el servidor.
- Estados `PENDIENTE`, `CONFIRMADO`, `ENTREGADO` y `CANCELADO`.
- Cancelación con devolución del stock.
- Bloqueo de inventario durante una venta para evitar vender dos veces la última unidad.
- Gestión básica de planes mediante `/api/planes`.
- Respuestas de error JSON comprensibles.
- Datos iniciales de demostración cuando la base está vacía.

## Importante

El bloque llamado “Asistente IA” es actualmente una demostración visual. No envía mensajes reales ni se conecta con WhatsApp. Para producción todavía se necesita autenticación de administradores, una API oficial de WhatsApp, copias de seguridad y pruebas de seguridad.
=======
GymFit - Ready project (Thymeleaf + MySQL)
-----------------------------------------

What it includes:
- Login (persistent users saved in MySQL)
- Register (saves user)
- Index (requires login) with 3 options: Ejercicios, Rutinas, Productos
- Pages for menu/diet, routines, products, and product detail
- Seed data: 3 products, 3 diets, 3 routines, 3 meals, 1 example user (agus@example.com / 1234)
- Images: place your images in src/main/resources/static/img/
  vegetariano.jpg, omnivoro.jpg, vegano.jpg, agua.jpg, vitamina.jpg, barra.jpg

Run:
1) Ensure Java 21 is set as Project SDK.
2) Import as Maven project.
3) Configure MySQL if needed (port 3307 used by default in application.properties)
4) Run com.gymfit.GymfitApplication
5) Open http://localhost:8081/
# Gymfit-nuevo
>>>>>>> 9ed5cab6c1352019c8f48483b3392601332af483
