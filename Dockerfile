# Build stage
#FROM maven:3.9.6-eclipse-temurin-21 AS builder
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn dependency:go-offline
#RUN mvn clean install -DskipTests
#RUN JAR_FILE=$(ls /app/target/*.jar | grep -v "\.original" | head -n 1)

# Run stage
FROM openjdk:21-slim
COPY target/Seerbit-Transaction-service-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]