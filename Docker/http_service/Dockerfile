#Docker file for HttpService

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/HttpService

WORKDIR /HoneyPot/HttpService

ENTRYPOINT ["java", "-jar", "HttpService-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]


