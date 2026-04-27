🚀 MockEndpoint: Generador de APIs con IA

MockEndpoint es una herramienta de escritorio construida con Compose Multiplatform (Kotlin) que permite diseñar y desplegar endpoints de API falsos (mocks) utilizando lenguaje natural. Gracias a la potencia de n8n y Google Gemini, puedes describir qué datos necesitas y el sistema se encarga de crear la ruta, definir el método y generar datos sintéticos realistas.
🛠️ Arquitectura del Proyecto

El sistema se apoya en cuatro pilares fundamentales:

    Frontend: App de escritorio nativa en Kotlin (Compose Multiplatform).

    Orquestador: n8n ejecutándose en Docker para manejar la lógica y los webhooks.

    Cerebro: Google Gemini (IA) para interpretar las instrucciones y generar JSONs válidos.

    Base de Datos: PostgreSQL para persistir los endpoints creados.

📦 Requisitos Previos

    Docker y Docker Compose instalados.

    Una API Key de Google Gemini (puedes obtenerla en Google AI Studio).

    IntelliJ IDEA o Android Studio para ejecutar la App de escritorio.

🚀 Guía de Inicio Rápido
1. Levantar la infraestructura

Clona el repositorio y arranca los contenedores:

docker-compose up -d

Esto levantará n8n en el puerto 5678 y PostgreSQL en el 5432.
2. Configurar n8n

    Accede a http://localhost:5678.

    Importa los flujos situados en la carpeta /n8n del proyecto:

        EndpointMaker.json: El flujo que crea los endpoints.

        ApiMockServer.json: El flujo que sirve los datos (Mock Server).

    Configura tus credenciales de Google Gemini y Postgres en los nodos correspondientes.

    Activa ambos flujos con sus URL de producción (o de prueba).

3. Ejecutar la App

Abre el proyecto de Kotlin en tu IDE y ejecuta la tarea de Gradle:

./gradlew run

📖 Cómo funciona

    Describe tu API: En la App, escribe algo como: "Crea un GET en /users con 3 usuarios que tengan id, nombre y email".

    Procesamiento: La App envía la instrucción al Webhook de n8n.

    IA al rescate: Gemini interpreta la petición y devuelve un esquema JSON con datos realistas.

    Persistencia: n8n guarda el endpoint en la base de datos.

    ¡Listo para usar!: Ahora puedes hacer una petición a tu nuevo endpoint:
    GET http://localhost:5678/webhook/tu-ruta

📁 Estructura del Repositorio

    /MockingAsAService: Código fuente de la aplicación en Kotlin Compose.

    /n8nWorkflows: Archivos JSON con la lógica de los flujos de automatización.

    /Docker:
      +docker-compose.yml: Configuración de los servicios necesarios.
      +init.sql: Script de inicialización para la base de datos PostgreSQL.

🔒 Seguridad y Privacidad

Este proyecto está diseñado para uso local. Recuerda no subir nunca tus archivos .env o credenciales reales de n8n al repositorio. Los flujos incluidos en este repo han sido ofuscados para que debas configurar tus propias API Keys.

Desarrollado con ❤️ usando Kotlin y n8n.
