# Local Development Guide

This guide provides detailed instructions for setting up and running the project locally without Docker.

## Prerequisites

### Required Software
- Java JDK 17 or higher
- Maven 3.8.x or higher
- MySQL 8.0.x
- Git

### Installation Links
- [Java JDK](https://adoptium.net/)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [Git](https://git-scm.com/downloads)

## Environment Setup

### 1. Java Configuration
1. Install Java JDK 17
2. Set JAVA_HOME environment variable:
   ```bash
   # Windows
   set JAVA_HOME=C:\Program Files\Java\jdk-17

   # Linux/macOS
   export JAVA_HOME=/usr/lib/jvm/java-17
   ```
3. Verify installation:
   ```bash
   java -version
   ```

### 2. Maven Configuration
1. Install Maven
2. Add Maven to PATH
3. Verify installation:
   ```bash
   mvn -version
   ```

### 3. MySQL Setup
1. Install MySQL Server
2. Start MySQL service:
   ```bash
   # Windows
   net start mysql

   # Linux
   sudo systemctl start mysql

   # macOS
   brew services start mysql
   ```
3. Create database and user:
   ```sql
   CREATE DATABASE boilerplate;
   CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
   GRANT ALL PRIVILEGES ON boilerplate.* TO 'user'@'localhost';
   FLUSH PRIVILEGES;
   ```

## Project Setup

### 1. Clone Repository
```bash
git clone <repository-url>
cd boilerplate
```

### 2. Configure Application Properties
Create or modify `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/boilerplate
spring.datasource.username=user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Server Configuration
server.port=8081

# JWT Configuration
jwt.secret=your-secret-key
jwt.expiration=86400000
```

### 3. Build Project
```bash
mvn clean install -DskipTests
```

### 4. Run Application
```bash
mvn spring-boot:run
```

## Verification Steps

### 1. Check Application Status
Access the following URLs:
- Application: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui.html
- API Docs: http://localhost:8081/v3/api-docs

### 2. Test Endpoints
Use curl or Postman to test the endpoints:

1. Register a new user:
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
}'
```

2. Login:
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{
    "username": "testuser",
    "password": "password123"
}'
```

## Common Issues and Solutions

### Database Connection Issues
1. Verify MySQL is running:
   ```bash
   # Windows
   netstat -ano | findstr 3306

   # Linux/macOS
   lsof -i :3306
   ```

2. Check database credentials:
   ```sql
   mysql -u user -p
   SHOW DATABASES;
   ```

### Port Already in Use
1. Find process using port 8081:
   ```bash
   # Windows
   netstat -ano | findstr :8081

   # Linux/macOS
   lsof -i :8081
   ```

2. Kill the process:
   ```bash
   # Windows
   taskkill /PID <PID> /F

   # Linux/macOS
   kill -9 <PID>
   ```

### Maven Build Issues
1. Clear Maven cache:
   ```bash
   mvn clean
   rm -rf ~/.m2/repository
   ```

2. Update dependencies:
   ```bash
   mvn dependency:purge-local-repository
   mvn clean install
   ```

## Development Tips

### Hot Reload
Enable automatic restart during development by adding the following dependency:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Debug Mode
Run application in debug mode:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

### Logging
Modify logging levels in application.properties:
```properties
logging.level.root=INFO
logging.level.com.app=DEBUG
logging.file.name=application.log
```

## Additional Resources
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Maven Guide](https://maven.apache.org/guides/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
