#Docker file for UserInterface

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/UserInterface

WORKDIR /HoneyPot/UserInterface

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "UserInterface-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]


