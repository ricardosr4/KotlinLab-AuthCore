# KotlinLab AuthCore

Plantilla educativa y profesional de autenticacion para aplicaciones Android
modernas.

El proyecto busca construir una solucion reutilizable mientras se estudia y
documenta cada decision tecnica. No sera solamente una pantalla conectada
directamente a Firebase: la autenticacion se disenara mediante contratos
propios para mantener separadas la interfaz, el dominio y los proveedores
externos.

> Estado actual: proyecto Android inicial auditado. La arquitectura y las
> funcionalidades descritas en este documento forman parte del plan de
> implementacion.

## Objetivos

- Comprender completamente cada bloque antes de avanzar.
- Aplicar arquitectura y practicas utilizadas en proyectos reales.
- Mantener Firebase y otros proveedores detras de abstracciones propias.
- Construir funcionalidades pequenas, verificables y acompanadas de pruebas.
- Crear una base que pueda adaptarse a futuras aplicaciones Android.

## Alcance funcional

KotlinLab AuthCore incluira:

- Registro con correo electronico y contrasena.
- Inicio y cierre de sesion.
- Recuperacion de contrasena.
- Verificacion de correo electronico.
- Restauracion y observacion del estado de sesion.
- Inicio de sesion con Google.
- Inicio de sesion con Facebook.
- Proteccion local mediante biometria.
- Deep links para flujos de autenticacion.
- Validacion de formularios y manejo centralizado de errores.
- Estados de carga, feedback y reintentos.
- Pantalla privada de demostracion.

La primera version funcional se concentrara en:

- Registro con correo electronico y contrasena.
- Inicio y cierre de sesion.
- Restauracion de sesion.
- Recuperacion de contrasena.
- Pantalla privada.
- Validaciones, manejo de errores y pruebas.

Google, Facebook, biometria y deep links se incorporaran en etapas posteriores.

## Stack tecnologico

### Base Android

- Kotlin
- Android nativo
- Gradle con Kotlin DSL
- Version Catalog
- Kotlin Coroutines
- Kotlin Flow y StateFlow

### Interfaz y presentacion

- Jetpack Compose
- Material 3
- MVVM
- Flujo de datos unidireccional
- Enfoque MVI-like mediante `UiState`, `UiAction` y `UiEffect`
- Temas claro y oscuro
- Componentes reutilizables y previews

### Arquitectura y navegacion

- Clean Architecture
- Repository Pattern
- Casos de uso
- Inversion de dependencias
- Hilt
- Navigation 3 con rutas tipadas
- Deep links

### Autenticacion y seguridad

- Firebase Authentication como primera implementacion
- Credential Manager para autenticacion con Google
- SDK oficial de Facebook para Android
- AndroidX Biometric
- Mapeo de errores externos a errores propios del dominio
- Almacenamiento local limitado a informacion no sensible

La biometria se utilizara para proteger localmente una sesion existente. No
reemplazara la autenticacion remota ni almacenara credenciales del usuario.

### Calidad y pruebas

- JUnit
- Fakes para pruebas aisladas
- Pruebas unitarias de validadores, casos de uso y ViewModels
- Compose UI Test
- Pruebas instrumentadas para integraciones Android
- Android Lint
- Integracion continua con GitHub Actions

## Arquitectura propuesta

La capa de dominio no conocera Firebase, Compose ni clases del framework
Android. La interfaz tampoco se comunicara directamente con Firebase.

```text
Compose UI
    | UiAction
    v
ViewModel
    | Use Case
    v
AuthRepository
    | implementacion
    v
Firebase / proveedores externos
```

Reglas principales:

1. La UI observa una unica fuente de verdad representada por `UiState`.
2. Los eventos del usuario llegan al ViewModel como `UiAction`.
3. Los eventos de una sola ejecucion se representan mediante `UiEffect`.
4. Los ViewModels dependen de casos de uso.
5. Los casos de uso dependen de contratos del dominio.
6. La capa de datos implementa los contratos y adapta servicios externos.
7. Los modelos y errores externos no atraviesan las fronteras de su capa.

## Modularizacion propuesta

La estructura definitiva se aprobara antes de crear los modulos.

```text
:app
:core:common
:core:designsystem
:core:navigation
:core:testing
:domain:auth
:data:auth
:feature:login
:feature:register
:feature:passwordrecovery
:feature:home
```

| Modulo | Responsabilidad |
| --- | --- |
| `:app` | Punto de entrada, configuracion global y navegacion raiz |
| `:core:common` | Abstracciones y utilidades compartidas |
| `:core:designsystem` | Tema y componentes visuales reutilizables |
| `:core:navigation` | Rutas y contratos de navegacion |
| `:core:testing` | Fakes, reglas y utilidades para pruebas |
| `:domain:auth` | Modelos, contratos y casos de uso de autenticacion |
| `:data:auth` | Implementaciones y adaptadores de proveedores externos |
| `:feature:*` | Pantallas, estados y ViewModels de cada funcionalidad |

## Estado actual

El repositorio contiene una aplicacion Android minima con un unico modulo
`:app`.

Validaciones realizadas sobre la base inicial:

- Compilacion debug correcta.
- APK debug generado correctamente.
- Prueba unitaria inicial correcta.
- Android Lint sin errores.

Todavia no estan implementados la modularizacion, Hilt, Navigation 3,
Firebase ni los flujos de autenticacion.

## Requisitos actuales

- Android Studio con soporte para el Android Gradle Plugin utilizado.
- JDK 21. Puede utilizarse el JDK incluido con Android Studio.
- Android SDK configurado mediante `local.properties`.

El namespace y `applicationId` aprobados para el proyecto son:

```text
com.kotlinlab.authcore
```

El proyecto utilizara `minSdk = 26`, compatible con Android 8.0 o superior.

En Windows, las verificaciones principales se ejecutan con:

```powershell
.\gradlew.bat testDebugUnitTest lintDebug assembleDebug
```

## Roadmap resumido

1. Aprobar decisiones base y alcance de la primera version.
2. Comprender Gradle y crear la modularizacion.
3. Configurar calidad, pruebas y automatizacion.
4. Construir el sistema de diseno.
5. Modelar el dominio de autenticacion.
6. Integrar Firebase detras de contratos propios.
7. Implementar Hilt, Navigation 3 y los flujos principales.
8. Integrar Google y Facebook.
9. Implementar biometria y deep links.
10. Endurecer seguridad, pruebas, release y documentacion.

El backlog detallado, las decisiones y el registro de aprendizaje se mantienen
en la pagina de
[KotlinLab AuthCore en Notion](https://app.notion.com/p/KotlinLab-AuthCore-37ad9c480a258061b1ecc33d3857b034).

## Flujo de trabajo con Git

El repositorio utiliza:

- `master` para versiones estables.
- `develop` para integrar el trabajo aprobado de la siguiente version.
- Ramas creadas desde `develop` para cada tarea.

Prefijos de ramas:

```text
feature/<nombre>
fix/<nombre>
docs/<nombre>
refactor/<nombre>
test/<nombre>
build/<nombre>
```

Los commits seguiran Conventional Commits con mensajes breves en ingles:

```text
feat: add email validation
fix: handle expired session
docs: document git workflow
test: add login use case tests
build: configure hilt dependencies
```

Antes de integrar una rama se revisaran sus cambios y se ejecutaran la
compilacion, las pruebas y lint que correspondan. La
[guia de trabajo con Git](https://app.notion.com/p/37ad9c480a25811d9683d22899ed9896)
contiene el procedimiento completo y sus criterios de integracion.

## Decisiones pendientes

Antes de comenzar la implementacion se deben aprobar:

- Versiones definitivas y compatibles de las dependencias.
- Grafo final de modulos.
- Estrategia detallada de releases.
- Licencia del repositorio.

## Metodologia de trabajo

Cada etapa seguira el mismo ciclo:

1. Comprender el problema.
2. Definir responsabilidades y criterios de aceptacion.
3. Documentar la decision.
4. Implementar una seccion pequena.
5. Explicar el codigo y resolver dudas.
6. Agregar pruebas acordes al riesgo.
7. Verificar el comportamiento.
8. Actualizar la documentacion.

Una tarea se considerara terminada cuando:

- Ricardo confirme que comprende el problema y la solucion.
- Se cumplan sus criterios de aceptacion.
- La compilacion, pruebas y lint correspondientes hayan sido revisados.
- La documentacion necesaria este actualizada.
- El diff no contenga cambios accidentales ni secretos.

Solo se avanzara a la siguiente tarea cuando Ricardo confirme que comprende la
tarea actual, esta se encuentre terminada y autorice explicitamente continuar.

La prioridad del proyecto es aprender con profundidad y mantener una base
tecnica clara, verificable y reutilizable.
