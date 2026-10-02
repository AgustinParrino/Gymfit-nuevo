#RESUMEN GENERAL (QUITAMOS DIETAS NO LLEGAMOS )(Si quieren agregarlo gaston juan santi mandenle)
-------------------------------------------------------------
#como funciona 
entras con admin y la contraseña que te venga a dar el comando de aca , es facil si quieren modificarlo haganlo
(tenes que hacer lo de contraseñas de aplicacion gaston y yo me encargo de los correos de recuperacion)

--------------------------------------------------

Esta todo concon traseña BCrypt.(Cifradisimo papa ) Cambiar la variable inicial no modifica una cuenta existente.
No hay registro público ni recuperación por email. 
Spring Security protege las rutas mediante sesión, rol ADMIN y tokens CSRF. Las escrituras usan POST y validación de
servidor. La app usa un único catálogo y no modela socios. No exponer en Internet sin HTTPS y configuración del entorno.
-------------------------------------------------------------------
estructura :
- `src/main/java/com/gymfit`: entidades, repositorios, controlador MVC, seguridad y datos iniciales.
- `src/main/resources/templates`: vistas Thymeleaf.
- `src/main/resources/static`: estilos, JavaScript y favicon.
- `src/test/java/com/gymfit`: pruebas de integración y seguridad.


dejo las bases para todo beibes  suerte 