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

## Configuración Git (primer commit)
```bash
git init
git add .
git commit -m "Configuración inicial del proyecto"
git branch -M main
git remote add origin git@github.com:doblej0ta/motor-priorizacion.git
git push -u origin main
```
