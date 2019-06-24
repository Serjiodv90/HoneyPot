#Docker file for Monitor

FROM openjdk:8-jdk-alpine

COPY . /honeyPot/monitor

WORKDIR /honeyPot/monitor

RUN ls -a

ENTRYPOINT ["java", "-jar", "Monitor-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]

