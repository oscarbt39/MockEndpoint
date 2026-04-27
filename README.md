# 🚀 MockEndpoint: Generador de APIs con IA

**MockEndpoint** es una herramienta de escritorio construida con **Compose Multiplatform (Kotlin)** que permite diseñar y desplegar endpoints de API falsos (mocks) utilizando lenguaje natural. 

Gracias a la potencia de **n8n** y **Google Gemini**, puedes describir qué datos necesitas y el sistema se encarga de crear la ruta, definir el método y generar datos sintéticos realistas.

---

### 🛠️ Arquitectura del Proyecto

El sistema se apoya en cuatro pilares fundamentales:

* **Frontend:** App de escritorio nativa en Kotlin (Compose Multiplatform).
* **Orquestador:** n8n ejecutándose en Docker para manejar la lógica y los webhooks.
* **Cerebro:** Google Gemini (IA) para interpretar las instrucciones y generar JSONs válidos.
* **Base de Datos:** PostgreSQL para persistir los endpoints creados.

---

### 📦 Requisitos Previos

* ✅ **Docker** y **Docker Compose** instalados.
* 🔑 Una **API Key** de Google Gemini (puedes obtenerla en [Google AI Studio](https://aistudio.google.com/)).
* 💻 **IntelliJ IDEA** o **Android Studio** para ejecutar la App de escritorio.

---

### 🚀 Guía de Inicio Rápido

#### 1. Levantar la infraestructura
Clona el repositorio y arranca los contenedores:
```bash
docker-compose up -d
