@'
# ProyectoREST (Servidor + Cliente)

Este proyecto consiste en:
- Un **servidor REST** en **Spring Boot** (puerto **12345**) conectado a una base de datos **SQLite**.
- Un **cliente Java por consola** que consume el API REST y permite hacer operaciones CRUD.

## Estructura del repositorio
- `server/` → servidor Spring Boot (REST + BBDD)
- `client/` → cliente Java por consola (consume REST)
- `entregable/` → carpeta preparada para el ZIP final (JARs, docs HTML y PDF)

## Requisitos
- Java 17
- Maven 3.8+

## Compilar (cuando el proyecto esté completo)
```bash
mvn clean package
