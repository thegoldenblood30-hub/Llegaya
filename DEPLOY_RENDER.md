# Desplegar LlegaYA en Render

## Preparar el repositorio

1. Crea un repositorio en GitHub.
2. Sube todo el proyecto, incluyendo `Dockerfile` y `render.yaml`.
3. Entra en https://dashboard.render.com y selecciona **New > Blueprint**.
4. Conecta el repositorio y confirma el archivo `render.yaml`.

Render construirá la imagen con Java 25 y expondrá el servicio usando el puerto asignado por `PORT`.

## URL resultante

Render entregará una URL parecida a:

```text
https://llegaya-backend.onrender.com
```

La página estará en `/` y el estado del backend en `/api/health`.

## Base de datos

La configuración actual usa H2 en memoria como respaldo local. En Render los datos se perderán cuando el servicio se reinicie o duerma.

Para producción, crea una base PostgreSQL en Render y configura estas variables en el Web Service:

- `SPRING_DATASOURCE_URL`: URL JDBC PostgreSQL, por ejemplo `jdbc:postgresql://host:5432/database`
- `SPRING_DATASOURCE_USERNAME`: usuario de PostgreSQL
- `SPRING_DATASOURCE_PASSWORD`: contraseña de PostgreSQL
- `SPRING_DATASOURCE_DRIVER`: `org.postgresql.Driver`
- `H2_CONSOLE_ENABLED`: `false`

No publiques H2 Console en Internet. Está desactivada por `render.yaml`.
