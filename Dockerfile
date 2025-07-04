# Stage 1: Build the Spring Boot app
FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only what's needed
COPY pom.xml .
COPY src ./src

# Optional: Use a faster dependency resolution layer
RUN mvn dependency:resolve

# Build the application (no tests for faster pipeline)
RUN mvn clean package -DskipTests

# Stage 2: Run the app using a lighter base image
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port used by Spring Boot
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
