# Build stage
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Set environment variables
ENV PORT=8080
ENV SPRING_AI_OPENAI_API_KEY=${OPENAI_API_KEY}

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["--server.port=${PORT}"]
