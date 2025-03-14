# Stage 1: Building the JAR
FROM eclipse-temurin:21-jdk-jammy as builder
WORKDIR /app
COPY . .
RUN nmvn clean package -DskipTests

# Stage 2: Final Image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/app-1.0-SNAPSHOT.jar /app.jar
COPY wait-for-it.sh /wait-for-it.sh
# install default-mysql-client for the script wait-for-it.sh
RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*
RUN chmod +x /wait-for-it.sh

EXPOSE 8081
# Command to run the application
CMD ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "/app.jar"]