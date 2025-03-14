# Essential Commands and Installation Guide

## Docker Installation

### Windows
1. Download Docker Desktop for Windows from [Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-windows)
2. Ensure virtualization is enabled in BIOS
3. Run the downloaded installer
4. Follow the installation wizard
5. Restart the system when prompted
6. Verify installation by opening a terminal and running:
   ```bash
   docker --version
   docker-compose --version
   ```

### macOS
1. Download Docker Desktop for Mac from [Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-mac)
2. Drag Docker.app to Applications folder
3. Double-click Docker.app to start Docker
4. Verify installation by opening a terminal and running:
   ```bash
   docker --version
   docker-compose --version
   ```

### Linux (Ubuntu/Debian)
1. Update packages:
   ```bash
   sudo apt-get update
   ```
2. Install required dependencies:
   ```bash
   sudo apt-get install apt-transport-https ca-certificates curl software-properties-common
   ```
3. Add Docker's official GPG key:
   ```bash
   curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
   ```
4. Add Docker repository:
   ```bash
   sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
   ```
5. Install Docker:
   ```bash
   sudo apt-get update
   sudo apt-get install docker-ce docker-compose
   ```
6. Verify installation:
   ```bash
   docker --version
   docker-compose --version
   ```

## Project Execution Commands

### Project Setup
1. Build Java project:
   ```bash
   mvn clean package -DskipTests
   ```

2. Grant execution permissions to wait-for-it script:
   ```bash
   chmod +x wait-for-it.sh
   ```

### Docker Container Management
1. Stop and remove existing containers:
   ```bash
   docker-compose down
   ```

2. Build images (no cache):
   ```bash
   docker-compose build --no-cache
   ```

3. Start containers:
   ```bash
   docker-compose up
   ```

4. Start containers in background:
   ```bash
   docker-compose up -d
   ```

5. View container logs:
   ```bash
   docker-compose logs -f
   ```

### Port Management

#### Check ports in use

Windows:

```bash
    netstat -ano | findstr :8080
```

Linux/macOS:

```bash
    sudo lsof -i :8080
```

#### Free up ports

Windows:
```bash
    taskkill /PID <PID> /F
```

Linux/macOS:
```bash
    sudo kill -9 <PID>
```


## Application Endpoints

### Main URLs
- Application: http://localhost:8081
- OpenAPI Documentation: http://localhost:8081/v3/api-docs
- Swagger UI: http://localhost:8081/swagger-ui.html

### REST API Endpoints
- GET /api/users - List all users
- GET /api/users/{id} - Get user by ID
- POST /api/users - Create new user
- PUT /api/users/{id} - Update user
- DELETE /api/users/{id} - Delete user

### Authentication Endpoints
- POST /api/auth/login - Login
- POST /api/auth/register - Register new user
- POST /api/auth/refresh - Refresh token
- GET /api/auth/profile - Get user profile

## Common Troubleshooting

1. Port in use error:
   - Check which process is using the port
   - Kill the process or change port in application.properties

2. Docker permissions error:
   - For Linux, ensure user is in docker group:
     ```bash
     sudo usermod -aG docker $USER
     ```
   - Restart session

3. Database connection error:
   - Verify containers are running:
     ```bash
     docker-compose ps
     ```
   - Check logs:
     ```bash
     docker-compose logs db
     ```

## Additional Resources

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Maven Documentation](https://maven.apache.org/guides/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.