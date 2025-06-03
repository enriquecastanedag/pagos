FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle

COPY src src

RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon
RUN cp build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
