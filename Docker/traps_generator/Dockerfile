#Docker file for TrapGenerator

FROM openjdk:8-jdk-alpine

COPY . /HoneyPot/TrapsGenerator

WORKDIR /HoneyPot/TrapsGenerator

RUN apk add --no-cache ttf-dejavu

ENTRYPOINT ["java", "-jar", "TrapsGenerator-0.0.1-SNAPSHOT.jar", "--spring.config.additional-location=file:DockerApp.properties"]

