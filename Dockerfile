FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/ynd-task-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD curl --fail http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
