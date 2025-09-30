FROM eclipse-temurin:17-jre

WORKDIR /app

COPY target/banking-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8089

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
