Actividades 
- Parrino :
desarrollo de el sistema de Login . register / pantalla de admin(80%) 
- (Creacion de sus api respectiva)
- generacion de qr
- correo para recuperar
------------------------------------------------------
### 📝 Estado de Tareas: Vistas de Ejercicios y Dietas

**Santiago (Terminado):**
* ✔️ **Módulo de Rutinas:** Desarrollo del backend (`RoutineController`, `RoutineService`) y la vista frontal (`routines.html`) respetando el diseño base del proyecto.
* ✔️ **Solución Base de Datos:** Cambié la configuración en `application.properties` para utilizar **H2** (base de datos integrada). Esto soluciona el error de "Access denied" de MySQL al clonar el repositorio en distintas computadoras. El proyecto ahora arranca directo sin instalar nada.
* ✔️ **Datos de prueba:** Creé un script (`RoutineDataLoader`) que inyecta rutinas de ejemplo automáticamente al levantar el servidor.
* *Nota técnica:* En el modelo `Routine`, agregué `@Column(name = "routine_day")` a la variable `day` para solucionar un error de sintaxis SQL con palabras reservadas.

**Juan (Pendiente - Módulo de Dietas):**
Juan, te dejo asignado el módulo de nutrición. Como ya armé la arquitectura para las rutinas, podés seguir exactamente la misma lógica para que te sea más fácil:
* 🔲 **Backend:** Crear `DietController` y `DietService` para manejar los modelos de `Diet` y `Meal`.
* 🔲 **Datos Iniciales:** Armar un `DietDataLoader` (podés copiar la estructura del mío) para que tengamos dietas de prueba al abrir la app.
* 🔲 **Vista:** Crear el archivo `diets.html`. ¡Aprovechá la estructura y las clases CSS que dejé en `routines.html` así mantenemos el mismo estilo visual (fondo oscuro, botones neón) en todo el sistema!
---------------------------------------------------

Gaston 
-encargado de crear las vistas actuales , sos un genio de ultima hace de apoyo conmigo en las tareas conmigo o automatizar en su defecto cuando sea necesario 