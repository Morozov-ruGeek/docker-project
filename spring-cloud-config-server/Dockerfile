FROM openjdk:11.0.12-jre-buster
RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG JAR_FILE=build/libs/spring-cloud-config.jar
COPY ${JAR_FILE} spring-cloud-config.jar
ENTRYPOINT ["java","-jar","spring-cloud-config.jar"]