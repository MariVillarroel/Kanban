# Kanban - Dev7 entregables (Security, Notifications) + Dev5 (Verify UI)

Listo para abrir en **Visual Studio Code** y correr con `mvn spring-boot:run`.

## Endpoints clave
- `POST /api/auth/confirm?token=` → usado por **/verificar-cuenta.html** para activar cuenta.  
- `POST /api/tasks` → crea tarea y dispara **TaskCreatedEvent** → notificación in‑app y, si la preferencia del usuario lo permite, email asíncrono.

## Flujo de errores unificado
Todas las excepciones de dominio devuelven:
```json
{ "error": { "code": "XXX", "message": "..." } }
```
Incluye casos: **NO_PERMISSION**, **USER_INACTIVE**, **TASK_NOT_FOUND**, **INVALID_TOKEN**, y **RATE_LIMIT** (HTTP 429).

## Seguridad
- **AssignmentSecurity** impide que un líder asigne fuera de su proyecto.
- **RateLimitFilter** (Bucket4j) – 100 req/min por IP.
- **Sanitizer** – util simple para normalizar/escapar entradas.

## Notificaciones
- Evento `TaskCreatedEvent` → **NotificationService** crea notificación in‑app (persistida) y, si `NotificationPreference.email=true`, envía email asíncrono (`@Async`).
- **Auditoría mínima** en `notification_logs` (canal, estado, error).

## Frontend
- Página **/verificar-cuenta.html** (estático) consume `/api/auth/confirm?token=` y muestra **éxito** o **error**.

## Cómo correr
```bash
# Requisitos: Java 17+, Maven 3.9+
mvn spring-boot:run
# abrir http://localhost:8080/verificar-cuenta.html?token=demo
```

## Ajustes pendientes para producción
- Reemplazar el placeholder de email por el correo real del usuario (lookup por userId).
- Implementar almacenamiento real de watchers/suscriptores.
- Integrar con tu modelo/paquetes actuales si difieren.
