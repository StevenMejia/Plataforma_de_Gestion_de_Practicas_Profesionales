# Etapa de construcción
FROM maven:3.9-eclipse-temurin AS builder

WORKDIR /app
COPY . .

# Ejecuta el build completo de frontend Vaadin + empaquetado
RUN mvn vaadin:prepare-frontend vaadin:build-frontend
RUN mvn clean package -DskipTests

# Etapa final (solo JAR)
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
