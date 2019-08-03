FROM ubuntu:16.04

USER root

WORKDIR /app

COPY target/whereami-0.0.1-SNAPSHOT.jar /app

RUN cd /app

RUN apt-get update

RUN apt-get install -y openjdk-8-jdk

EXPOSE 8080

ENTRYPOINT "java" "-jar" "whereami-0.0.1-SNAPSHOT.jar" "--spring.datasource.url=$DB_URL" "--spring.datasource.username=$DB_USERNAME" "--spring.datasource.password=$DB_PASS"
