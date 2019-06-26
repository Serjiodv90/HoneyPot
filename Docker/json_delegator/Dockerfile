#Docker file for JsonDelegator

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/JsonDelegator

WORKDIR /HoneyPot/JsonDelegator

ENTRYPOINT ["java", "-jar", "JsonDelegator-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]


