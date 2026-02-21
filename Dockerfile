# Etapa 1: Construcción (Build)
FROM gradle:9.3.0-jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Compilamos el JAR saltando los tests para acelerar el despliegue
RUN gradle build --no-daemon -x test

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]