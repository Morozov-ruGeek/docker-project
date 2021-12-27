FROM openjdk:11.0.12-jre-buster
RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG JAR_FILE=build/libs/eureka-server.jar
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]