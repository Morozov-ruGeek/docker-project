FROM openjdk:11.0.12-jre-buster
RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG JAR_FILE=build/libs/gateway-service.jar
COPY ${JAR_FILE} gateway-service.jar
ENTRYPOINT ["java","-jar","gateway-service.jar"]