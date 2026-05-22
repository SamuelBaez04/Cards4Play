# --- Fase 1: Compilación (Build) ---
FROM gradle:8.14-jdk21-alpine AS build
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle bootJar --no-daemon

# --- Fase 2: Imagen de Ejecución (Run) ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar
RUN mkdir -p /app/data
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]