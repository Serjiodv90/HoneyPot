#Docker file for TrapGenerator

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/TrapsGenerator

WORKDIR /HoneyPot/TrapsGenerator

RUN apk add --no-cache ttf-dejavu

ENTRYPOINT ["java", "-jar", "TrapsGenerator-1.0.0.jar", "--spring.config.additional-location=file:DockerApp.properties"]

