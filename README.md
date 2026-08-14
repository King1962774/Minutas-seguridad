# Minutas Seguridad - Sistema de Control de Portería y Vigilancia

Sistema de escritorio comercial desarrollado en **Java 17** y **JavaFX 21** para la gestión integral de porterías, control de visitantes, vehículos, correspondencia, rondas y turnos en conjuntos residenciales. Cuenta con arquitectura desacoplada lista para migrar a un backend en la nube (API REST).

---

## Requisitos Previos

- **Liberica Full JDK 21** (recomendado, ya que incluye JavaFX embebido y evita configuraciones manuales de module-path), o cualquier JDK 21 estándar.
- **Apache Maven 3.8+**.

---

## Cómo Ejecutar la Aplicación

Puedes hacer doble clic en el archivo **`run.bat`** (en Windows) o ejecutar en la terminal:

```bash
mvn javafx:run
```

Al iniciar por primera vez, el sistema creará automáticamente la base de datos SQLite en `db/minutas.db` ejecutando los scripts de `schema.sql` y `seed.sql`.

### Credenciales de Prueba (Seed)
- **Administrador:** `admin` / `admin123`
- **Supervisor:** `supervisor` / `super123`
- **Vigilante:** `vigilante` / `vigi123`

---

## Pruebas Unitarias

Para ejecutar las pruebas unitarias con JUnit 5 y Mockito:

```bash
mvn test
```

---

## Empaquetado Final con `jpackage`

Para generar un instalador nativo de escritorio mediante `jpackage`:

1. Generar el JAR con dependencias:
   ```bash
   mvn clean package
   ```
2. Ejecutar `jpackage`:
   ```bash
   jpackage --type app-image \
     --name "MinutasSeguridad" \
     --input target/ \
     --dest target/installer \
     --main-jar minutas-seguridad-1.0.0.jar \
     --main-class com.minutas.App
   ```

---

## Ruta de Migración a Backend (API REST en la nube)

La arquitectura del sistema está diseñada bajo el patrón de repositorios (Dependency Injection manual):

1. **Desacoplamiento:** Las clases en `com.minutas.service/` dependen exclusivamente de interfaces en `com.minutas.repository/` (ej. `VisitanteRepository`), sin importar bibliotecas SQL directamente.
2. **Implementación API:** Para migrar a un backend remoto, solo es necesario crear una nueva implementación que consuma la API REST (ej. `ApiVisitanteRepository` usando `HttpClient` de Java o Retrofit/Jackson):
   ```java
   public class ApiVisitanteRepository implements VisitanteRepository {
       // Realiza peticiones HTTP POST/GET al servidor cloud y deserializa con Jackson
   }
   ```
3. **Inyección:** Reemplazar la instanciación de `SqliteVisitanteRepository` por `ApiVisitanteRepository` en los servicios, manteniendo intacta toda la lógica de negocio y la interfaz gráfica JavaFX (Offline-first ready con cola local de sincronización).

---

## Despliegue del Servidor en VPS (HTTPS)

Para desplegar el servidor headless 24/7 en un VPS:

1. **Contratar un VPS** (Hetzner, DigitalOcean, AWS Lightsail, etc.).
2. **Apuntar un subdominio** (ej. `minutas.tudominio.com`) a la IP del VPS (registro DNS tipo A).
3. **Instalar Liberica Full JDK 21** en el VPS.
4. **Subir el `.jar`** empaquetado del proyecto a `/opt/minutas`.
5. **Configurar Caddy** usando el archivo `deploy/Caddyfile` para HTTPS automático.
6. **Registrar el servicio systemd** usando `deploy/minutas-server.service` en `/etc/systemd/system/minutas-server.service`:
   ```bash
   sudo systemctl daemon-reload
   sudo systemctl enable --now minutas-server
   ```
7. **Abrir puertos 80 y 443** en el firewall del VPS.

