#Docker file for FtpService

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/FtpService

WORKDIR /HoneyPot/FtpService

VOLUME /HoneyPot/FtpService/FtpHome

ENTRYPOINT ["java", "-jar", "FtpService-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]


