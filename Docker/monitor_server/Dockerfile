#Docker file for Monitor

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/Monitor

WORKDIR /HoneyPot/Monitor

ENTRYPOINT ["java", "-jar", "Monitor-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]

