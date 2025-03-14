# Guía de Desarrollo Local

Esta guía proporciona instrucciones detalladas para configurar y ejecutar el proyecto localmente sin Docker.

## Requisitos Previos

### Software Necesario
- Java JDK 21 o superior (LTS)
- Maven 3.9.6 o superior
- MySQL 8.0.x
- Git

### Enlaces de Instalación
- [Java JDK](https://adoptium.net/) (Seleccionar versión 21 LTS)
- [Maven](https://maven.apache.org/download.cgi) (Última versión: 3.9.6)
- [MySQL](https://dev.mysql.com/downloads/mysql/) (Versión 8.0)
- [Git](https://git-scm.com/downloads)

## Configuración del Entorno

### 1. Configuración de Java
1. Instalar Java JDK 21
2. Configurar la variable de entorno JAVA_HOME:
   ```bash
   # Windows
   set JAVA_HOME=C:\Program Files\Java\jdk-21

   # Linux/macOS
   export JAVA_HOME=/usr/lib/jvm/java-21
   ```
3. Verificar la instalación:
   ```bash
   java -version
   # Debería mostrar: openjdk version "21.0.x"
   ```

### 2. Configuración de Maven
1. Descargar Maven 3.9.6
2. Extraer el archivo y agregar el directorio bin al PATH
3. Verificar la instalación:
   ```bash
   mvn -version
   # Debería mostrar: Apache Maven 3.9.6
   ```

### 3. Configuración de MySQL
1. Instalar MySQL Server
2. Iniciar el servicio de MySQL:
   ```bash
   # Windows
   net start mysql

   # Linux
   sudo systemctl start mysql

   # macOS
   brew services start mysql
   ```
3. Crear base de datos y usuario:
   ```sql
   CREATE DATABASE boilerplate;
   CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON boilerplate.* TO 'user'@'localhost';
   FLUSH PRIVILEGES;
   ```

## Configuración del Proyecto

### 1. Clonar Repositorio
```bash
git clone <repository-url>
cd boilerplate
```

### 2. Configurar Propiedades de la Aplicación
Crear o modificar `src/main/resources/application.properties`:
```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/boilerplate
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Configuración del Servidor
server.port=8081

# Configuración JWT
jwt.secret=your-secret-key
jwt.expiration=86400000
```

### 3. Construir Proyecto
```bash
mvn clean install -DskipTests
```

### 4. Ejecutar Aplicación
```bash
mvn spring-boot:run
```

## Pasos de Verificación

### 1. Verificar Estado de la Aplicación
Acceder a las siguientes URLs:
- Aplicación: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui.html
- Documentación API: http://localhost:8081/v3/api-docs

### 2. Probar Endpoints
Usar curl o Postman para probar los endpoints:

1. Registrar un nuevo usuario:
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
}'
```

2. Iniciar sesión:
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{
    "username": "testuser",
    "password": "password123"
}'
```

## Problemas Comunes y Soluciones

### Problemas de Conexión a Base de Datos
1. Verificar que MySQL esté ejecutándose:
   ```bash
   # Windows
   netstat -ano | findstr 3306

   # Linux/macOS
   lsof -i :3306
   ```

2. Verificar credenciales de base de datos:
   ```sql
   mysql -u user -p
   SHOW DATABASES;
   ```

### Puerto en Uso
1. Encontrar proceso usando el puerto 8081:
   ```bash
   # Windows
   netstat -ano | findstr :8081

   # Linux/macOS
   lsof -i :8081
   ```

2. Terminar el proceso:
   ```bash
   # Windows
   taskkill /PID <PID> /F

   # Linux/macOS
   kill -9 <PID>
   ```

### Problemas con Maven
1. Limpiar caché de Maven:
   ```bash
   mvn clean
   rm -rf ~/.m2/repository
   ```

2. Actualizar dependencias:
   ```bash
   mvn dependency:purge-local-repository
   mvn clean install
   ```

## Consejos de Desarrollo

### Recarga en Caliente
Habilitar reinicio automático durante el desarrollo agregando la siguiente dependencia:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Modo Debug
Ejecutar la aplicación en modo debug:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

### Registro (Logging)
Modificar niveles de registro en application.properties:
```properties
logging.level.root=INFO
logging.level.com.app=DEBUG
logging.file.name=application.log
```

## Recursos Adicionales
- [Documentación de Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Guía de Maven](https://maven.apache.org/guides/)
- [Documentación de MySQL](https://dev.mysql.com/doc/)

## Desarrollo con Visual Studio Code

### Extensiones Recomendadas
1. Extension Pack for Java
   - ID: `vscjava.vscode-java-pack`
   - Incluye:
     - Language Support for Java
     - Debugger for Java
     - Java Test Runner
     - Maven for Java
     - Project Manager for Java

2. Spring Boot Extension Pack
   - ID: `Pivotal.vscode-boot-dev-pack`
   - Incluye:
     - Spring Boot Tools
     - Spring Initializr
     - Spring Boot Dashboard

3. Otras extensiones útiles:
   - Thunder Client (alternativa a Postman)
   - GitLens
   - Lombok Annotations Support

### Configuración del Proyecto en VS Code
1. Abrir el proyecto:
   ```bash
   code .
   ```

2. Esperar a que VS Code indexe el proyecto y descargue las dependencias

3. Verificar la configuración de Java:
   - Presionar `Ctrl/Cmd + Shift + P`
   - Buscar "Java: Configure Java Runtime"
   - Asegurarse que esté seleccionado Java 21

### Ejecutar el Proyecto
Hay varias formas de ejecutar el proyecto en VS Code:

1. Usando Spring Boot Dashboard:
   - Abrir Spring Boot Dashboard (icono de Spring en la barra lateral)
   - Buscar el proyecto
   - Hacer clic en el botón de play ▶️

2. Usando el explorador de archivos:
   - Localizar `Application.java`
   - Hacer clic en `Run` sobre el método main

3. Usando la paleta de comandos:
   - `Ctrl/Cmd + Shift + P`
   - Buscar "Spring Boot: Run"

### Depuración (Debug)

1. Configuración del depurador:
   - VS Code creará automáticamente el archivo `launch.json`
   - Si no existe, crear la carpeta `.vscode` y el archivo `launch.json`:

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Debug Spring Boot Application",
            "request": "launch",
            "mainClass": "com.app.Application",
            "projectName": "boilerplate",
            "args": "",
            "envFile": "${workspaceFolder}/.env"
        }
    ]
}
```

2. Iniciar depuración:
   - Establecer puntos de interrupción haciendo clic en el margen izquierdo del editor
   - Presionar F5 o usar el menú de depuración
   - Usar los controles de depuración:
     - F5: Continuar
     - F10: Paso sobre (Step Over)
     - F11: Paso a (Step Into)
     - Shift + F11: Paso fuera (Step Out)

### Características Útiles

1. Spring Boot DevTools en VS Code:
   - La recarga en caliente funcionará automáticamente
   - Para recargar manualmente: `Ctrl/Cmd + Shift + P` -> "Spring Boot: Reload"

2. Vista de endpoints:
   - Abrir Spring Boot Dashboard
   - Expandir la sección "ENDPOINTS"
   - Ver todos los endpoints disponibles

3. Testing integrado:
   - Ver la pestaña de Testing en la barra lateral
   - Ejecutar tests individuales o todos los tests
   - Ver cobertura de código

4. Integración con Base de Datos:
   - Usar la extensión "Database Client" para conectar con MySQL
   - Ver y editar datos directamente desde VS Code

### Atajos de Teclado Útiles

- `Ctrl/Cmd + Shift + P`: Paleta de comandos
- `Ctrl/Cmd + P`: Búsqueda rápida de archivos
- `F5`: Iniciar depuración
- `Shift + F5`: Detener depuración
- `Ctrl/Cmd + Shift + D`: Abrir vista de depuración
- `Ctrl/Cmd + Shift + E`: Explorador de archivos
- `Ctrl/Cmd + Shift + X`: Extensiones
- `Alt + Shift + F`: Formatear código

### Solución de Problemas en VS Code

1. Java no reconocido:
   - Verificar Java Home en VS Code
   - `Ctrl/Cmd + Shift + P` -> "Java: Configure Java Runtime"

2. Problemas de dependencias:
   - Limpiar caché de Maven
   - Actualizar proyecto Maven (clic derecho en `pom.xml` -> Update Project)

3. Problemas de intellisense:
   - `Ctrl/Cmd + Shift + P` -> "Java: Clean Java Language Server Workspace"
   - Reiniciar VS Code

4. Problemas de depuración:
   - Verificar que el archivo `launch.json` esté correctamente configurado
   - Limpiar y reconstruir el proyecto
