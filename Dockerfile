FROM ubuntu:latest
LABEL authors="ASC"

# Etapa 1: build da aplicação
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY . .

RUN ./gradlew clean bootJar --no-daemon


# Etapa 2: execução
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]