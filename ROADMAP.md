boilerplate/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── app/
│   │   │           ├── auth/
│   │   │           │   ├── adapters/
│   │   │           │   │   └── AuthController.java
│   │   │           │   ├── application/
│   │   │           │   │   └── AuthService.java
│   │   │           │   ├── domain/
│   │   │           │   │   ├── IJwtTokenRepository.java
│   │   │           │   │   └── JwtToken.java
│   │   │           │   └── infrastructure/
│   │   │           │       ├── dto/
│   │   │           │       │   ├── AuthResponse.java
│   │   │           │       │   ├── LoginRequest.java
│   │   │           │       │   ├── ProfileResponse.java
│   │   │           │       │   ├── RefreshTokenRequest.java
│   │   │           │       │   └── RegisterRequest.java
│   │   │           │       ├── JwtRequestFilter.java
│   │   │           │       ├── JwtUtils.java
│   │   │           │       └── SecurityConfig.java
│   │   │           ├── shared/
│   │   │           │   ├── adapters/
│   │   │           │   │   └── exception/
│   │   │           │   │       └── ResourceNotFoundException.java
│   │   │           │   └── infrastructure/
│   │   │           ├── users/
│   │   │           │   ├── adapters/
│   │   │           │   │   └── UserController.java
│   │   │           │   ├── application/
│   │   │           │   │   ├── UserDetailsServiceImpl.java
│   │   │           │   │   └── UserService.java
│   │   │           │   ├── domain/
│   │   │           │   │   ├── ERole.java
│   │   │           │   │   ├── IUserRepository.java
│   │   │           │   │   ├── IUserService.java
│   │   │           │   │   └── User.java
│   │   │           │   └── infrastructure/
│   │   │           │       ├── UserDatasource.java
│   │   │           │       └── UserDto.java
│   │   │           └── Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── app/
│                   └── users/
│                       └── CrudUsersApplicationTests.java
├── .gitignore
├── CHANGELOG
├── CODEOWNERS
├── docker-compose.yml
├── Dockerfile
├── Dockerfile.dev
├── LICENSE
├── pom.xml
├── README.md
├── ROADMAP.md
├── VERSION
└── wait-for-it.sh