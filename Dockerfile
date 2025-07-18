# Stage 1: build with Gradle + JDK21
FROM gradle:8.6-jdk21 AS build

WORKDIR /app
COPY . .
RUN gradle build -x test

# Stage 2: runtime also on JDK21
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

