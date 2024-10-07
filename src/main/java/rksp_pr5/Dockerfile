FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /app

COPY . .

RUN gradle build -x test

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 80

CMD ["java", "-Dspring.datasource.url=jdbc:postgresql://host.docker.internal:5432/rksp5", "-Dspring.datasource.username=user", "-Dspring.datasource.password=password", "-jar", "app.jar"]
