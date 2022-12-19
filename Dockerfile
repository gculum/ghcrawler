#FROM openjdk:17-alpine
FROM openjdk:17-oracle
MAINTAINER gculum
WORKDIR /opt/ghcrawl/

COPY target/demo-0.0.1-SNAPSHOT.jar demo-0.0.1-SNAPSHOT.jar
COPY target/classes/application.yml application.yml
COPY target/classes/logback.xml logback.xml

ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]