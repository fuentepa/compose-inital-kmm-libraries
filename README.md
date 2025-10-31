# Compose Initial KMM Libraries

## 📋 Descripción del Proyecto

Este es un proyecto Android nativo desarrollado con **Jetpack Compose** que implementa una arquitectura limpia (Clean Architecture) modular. El proyecto está diseñado como una plantilla inicial para aplicaciones Android que consumen APIs REST, utilizando las mejores prácticas y las últimas tecnologías del ecosistema Android/Kotlin.

### Arquitectura del Proyecto

El proyecto está estructurado en **3 módulos principales**:

- **`app`**: Módulo de presentación que contiene la interfaz de usuario con Jetpack Compose, navegación y lógica de UI.
- **`domain`**: Módulo que contiene los casos de uso, entidades de negocio y contratos de repositorio.
- **`data`**: Módulo que implementa los repositorios, fuentes de datos (API REST, base de datos local) y modelos de datos.

### Tecnologías Principales

- **Jetpack Compose** - UI moderna y declarativa
- **Kotlin 2.2.21** - Lenguaje de programación
- **Koin** - Inyección de dependencias con anotaciones
- **Ktorfit + Ktor 3** - Cliente HTTP type-safe para API REST
- **Room** - Base de datos local
- **Coil 3** - Carga de imágenes con soporte Compose
- **DataStore** - Almacenamiento de preferencias
- **Navigation Compose** - Navegación entre pantallas
- **Material 3** - Sistema de diseño con componentes adaptativos
- **Kotlinx Serialization** - Serialización/deserialización JSON

### Características

- ✅ Arquitectura limpia modular
- ✅ Inyección de dependencias con Koin y KSP
- ✅ Networking type-safe con Ktorfit
- ✅ Persistencia local con Room
- ✅ UI moderna con Jetpack Compose y Material 3
- ✅ Flavors de desarrollo (dev/pro)
- ✅ Configuración de firma de APKs
- ✅ Integración con TMDB API (The Movie Database)

---

## 🚀 Cómo Clonar y Configurar el Proyecto

### Requisitos Previos

- **Android Studio** Ladybug (2024.2.1) o superior
- **JDK 21** o superior
- **Git** instalado en tu sistema
- SDK de Android con API Level 28-36

### Opción 1: Clonar el Repositorio Directamente

Para trabajar directamente con este repositorio en tu máquina local:

```bash
# 1. Clonar el repositorio
git clone https://github.com/TU_USUARIO/compose-inital-kmm-libraries.git

# 2. Navegar al directorio del proyecto
cd compose-inital-kmm-libraries

# 3. Abrir el proyecto con Android Studio
# Archivo > Abrir > Seleccionar la carpeta del proyecto
```

### Opción 2: Crear un Fork (Copia con Otro Nombre)

Si deseas crear tu propia copia del proyecto con un nombre diferente:

#### Desde GitHub (Interfaz Web)

1. **Hacer Fork del repositorio:**
   - Ve al repositorio original en GitHub: `https://github.com/USUARIO_ORIGINAL/compose-inital-kmm-libraries`
   - Haz clic en el botón **"Fork"** (esquina superior derecha)
   - Selecciona tu cuenta de GitHub como destino
   - Opcionalmente, cambia el nombre del repositorio en la pantalla de fork

2. **Clonar tu fork:**
```bash
# Clona tu fork (no el original)
git clone https://github.com/TU_USUARIO/tu-nuevo-nombre-proyecto.git

# Navega al directorio
cd tu-nuevo-nombre-proyecto
```

3. **Renombrar el proyecto localmente:**
   
   Una vez clonado, debes cambiar el nombre del proyecto en estos archivos:

   - **`settings.gradle.kts`** - Línea 23:
     ```kotlin
     rootProject.name = "tu-nuevo-nombre-proyecto"
     ```

   - **`app/build.gradle.kts`** - Líneas 21 y 26:
     ```kotlin
     namespace = "com.tunuevopackage.nombre"
     applicationId = "com.tunuevopackage.nombre"
     ```

   - Renombra los paquetes de Kotlin en:
     - `app/src/main/kotlin/com/compose/kmplibs/` → `app/src/main/kotlin/com/tunuevopackage/nombre/`
     - `data/src/main/kotlin/` → actualizar estructura de paquetes
     - `domain/src/main/kotlin/` → actualizar estructura de paquetes

#### Desde Línea de Comandos (Git)

```bash
# 1. Clonar el repositorio original sin historial completo
git clone --depth 1 https://github.com/USUARIO_ORIGINAL/compose-inital-kmm-libraries.git tu-nuevo-nombre-proyecto

# 2. Navegar al nuevo directorio
cd tu-nuevo-nombre-proyecto

# 3. Eliminar el origen remoto original
git remote remove origin

# 4. Crear un nuevo repositorio en GitHub (desde la web) y luego conectarlo
git remote add origin https://github.com/TU_USUARIO/tu-nuevo-nombre-proyecto.git

# 5. Hacer push de tu código al nuevo repositorio
git push -u origin main
```

---

## 🛠️ Configuración Inicial

### 1. Crear archivo `local.properties`

Crea un archivo `local.properties` en la raíz del proyecto con la siguiente estructura:

```properties
# Ruta del SDK de Android (se genera automáticamente al abrir el proyecto)
sdk.dir=C\:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk

# Clave privada de API (opcional, descomentarla si la necesitas)
# privateApiKey=TU_CLAVE_PRIVADA_AQUI
```

### 2. Configurar Keystore para Desarrollo

El proyecto ya incluye un keystore de desarrollo en `keys/devKeyStore.jks`. Si necesitas crear uno propio:

```bash
keytool -genkey -v -keystore keys/devKeyStore.jks -alias devKeyAlias -keyalg RSA -keysize 2048 -validity 10000
```

Actualiza las credenciales en `keys/keystoreDev.properties`.

### 3. Sincronizar el Proyecto

1. Abre Android Studio
2. Abre el proyecto
3. Espera a que Gradle sincronice todas las dependencias
4. Construye el proyecto: **Build > Make Project**

### 4. Ejecutar la Aplicación

```bash
# Desde la terminal
./gradlew :app:assembleDevDebug

# O desde Android Studio
# Selecciona la variante "devDebug" y presiona Run ▶️
```

---

## 📂 Estructura del Proyecto

```
compose-initial-kmm-libraries/
├── app/                          # Módulo de presentación (UI)
│   ├── src/main/kotlin/          # Código fuente Compose
│   └── build.gradle.kts          # Configuración del módulo
├── data/                         # Módulo de datos
│   ├── src/main/kotlin/          # Repositorios, API, DB
│   └── build.gradle.kts
├── domain/                       # Módulo de dominio
│   ├── src/main/kotlin/          # Casos de uso, entidades
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml        # Catálogo de versiones centralizado
├── keys/                         # Keystores de firma
├── build.gradle.kts              # Configuración raíz
├── settings.gradle.kts           # Configuración de módulos
└── README.md                     # Este archivo
```

---

## 🏗️ Variantes de Compilación

El proyecto tiene dos **flavors** configurados:

- **dev**: Entorno de desarrollo con TMDB API
- **pro**: Entorno de producción

Y dos **build types**:

- **debug**: Versión de depuración
- **release**: Versión de producción (requiere configurar keystore)

Combinaciones disponibles:
- `devDebug`
- `devRelease`
- `proDebug`
- `proRelease`

---

## 📝 Licencia

Este proyecto es una plantilla de código abierto. Puedes usarlo libremente para tus proyectos.

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Haz fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añade nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

## 📧 Contacto

Para preguntas o sugerencias, abre un issue en el repositorio.

---

**¡Feliz codificación! 🎉**

