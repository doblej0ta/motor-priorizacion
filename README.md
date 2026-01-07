# Motor de Priorización de Solicitudes

Sistema que calcula y expone prioridades de solicitudes mediante un backend Spring Boot y un frontend React. Permite crear solicitudes, verlas y listarlas ordenadas según reglas de negocio.

## Funcionalidades principales
- Crear solicitudes con tipo, prioridad manual y usuario.
- Listar solicitudes y ver su prioridad calculada.
- Obtener listado priorizado según estrategia estándar.

## Tecnologías
- **Backend:** Java 17, Spring Boot 3.x, H2 en memoria, Maven.
- **Frontend:** React 18, Axios.
- Arquitectura en capas (controller, service, repository, model) y patrón Strategy para el cálculo de prioridad.

## Estructura del proyecto
```
motor-priorizacion/
├── backend/        # Código Java (Spring Boot)
├── frontend/       # Aplicación React
├── .gitignore
└── README.md
```
- **backend/**: API REST, reglas de priorización, persistencia H2.
- **frontend/**: SPA en React para gestionar y visualizar solicitudes.

## Requisitos previos
- Java 17 o superior
- Node.js 18 o superior
- Git

## Configuración
- Backend: sin configuración extra (H2 en memoria, consola en `/h2-console`).
- Frontend: archivo `frontend/.env` con:
  ```env
  REACT_APP_API_URL=http://localhost:8080/api
  ```

## Instalación y ejecución
1) Clonar el repositorio:
```bash
git clone git@github.com:doblej0ta/motor-priorizacion.git
cd motor-priorizacion
```

2) Backend (con wrapper Maven incluido):
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```
API disponible en `http://localhost:8080`.

Alternativa (si tienes problemas con `spring-boot:run`):
```bash
# Linux/macOS
java -jar backend/target/motor-priorizacion-0.0.1-SNAPSHOT.jar

# Windows (PowerShell)
java -jar .\backend\target\motor-priorizacion-0.0.1-SNAPSHOT.jar
```

3) Frontend:
```bash
cd frontend
npm install
npm start
```
App disponible en `http://localhost:3000`.

Ejecución rápida con scripts (en Windows usar .ps1, en Linux/macOS usar .sh):
- `./scripts/start-backend.sh` o `powershell -ExecutionPolicy Bypass -File .\scripts\start-backend.ps1`
- `./scripts/start-frontend.sh` o `powershell -ExecutionPolicy Bypass -File .\scripts\start-frontend.ps1`

## Pruebas rápidas (Postman)
- Crea un Environment `local` con `baseUrl = http://localhost:8080/api`.
- Requests:
  - POST `{{baseUrl}}/solicitudes` (JSON):
    ```json
    { "tipo": "INCIDENTE", "prioridadManual": 4, "usuario": "jdoe" }
    ```
  - GET `{{baseUrl}}/solicitudes`
  - GET `{{baseUrl}}/solicitudes/priorizadas`
  - En Postman usa Desktop Agent (no Cloud) para poder llamar a `localhost`.

## Endpoints API
- `POST /api/solicitudes` – Crea una solicitud.
  - Request ejemplo:
    ```json
    {
      "tipo": "INCIDENTE",
      "prioridadManual": 4,
      "usuario": "jdoe"
    }
    ```
  - Response ejemplo:
    ```json
    {
      "solicitudId": "INC-001",
      "tipo": "INCIDENTE",
      "prioridadManual": 4,
      "usuario": "jdoe",
      "fechaCreacion": "2026-01-07T13:00:00",
      "prioridadCalculada": 6.1
    }
    ```
- `GET /api/solicitudes` – Lista todas las solicitudes.
- `GET /api/solicitudes/priorizadas` – Lista todas ordenadas por prioridad calculada desc.

## Reglas de priorización
- Fórmula: `(prioridadManual * multiplicadorTipo) + antiguedadDias * 0.1`.
- Multiplicadores por tipo: INCIDENTE 1.5, REQUERIMIENTO 1.0, CONSULTA 0.8.
- Factor de antigüedad: cada día suma 0.1.
- Ejemplo: prioridadManual 4 para INCIDENTE con 2 días de antigüedad → `(4 * 1.5) + (2 * 0.1) = 6.2`.

## Arquitectura
- Capas: Controller → Service → Repository → Model.
- Strategy: interfaz `EstrategiaPriorizacion` y `CalculadorPrioridadEstandar` como implementación actual.
- Frontend separado consume API REST.

## Principios aplicados
- Código limpio y responsabilidades claras.
- SOLID y separación de capas.
- Comentarios solo para explicar lógica de negocio.

## Desarrollo futuro
- Persistencia en base de datos real (PostgreSQL/MySQL).
- Autenticación y autorización.
- Estrategias de priorización adicionales.
- Tests unitarios e integración.

## Problemas comunes
- Postman indica `DNSLookup: ENOTFOUND baseurl`: cambia la URL a `http://localhost:8080/api/...` o usa la variable `{{baseUrl}}` en un Environment. Asegúrate de usar Desktop Agent.
- `mvnw spring-boot:run` falla con `ClassNotFoundException` en rutas con espacios/acentos: usa la alternativa `java -jar backend/target/...` tras compilar (`./mvnw clean install`).

## Configuración Git (primer commit)
```bash
git init
git add .
git commit -m "Configuración inicial del proyecto"
git branch -M main
git remote add origin git@github.com:doblej0ta/motor-priorizacion.git
git push -u origin main
```
