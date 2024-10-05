FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN gradle build -x test

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "app.jar"]