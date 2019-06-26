#Docker file for SmtpService

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/SmtpService

WORKDIR /HoneyPot/SmtpService

ENTRYPOINT ["java", "-jar", "SmtpService-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]


