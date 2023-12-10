FROM openjdk:18-jdk-slim

WORKDIR /app

COPY target/webcrawler-1.0-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar", "crawler.properties"]
