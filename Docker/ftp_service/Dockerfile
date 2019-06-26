#Docker file for UserInterface

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/UserInterface

WORKDIR /HoneyPot/UserInterface

ENTRYPOINT ["java", "-jar", "UserInterface-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]


