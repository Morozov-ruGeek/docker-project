FROM openjdk:11.0.12-jre-buster
RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG JAR_FILE=build/libs/payment-service.jar
COPY ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java","-jar","payment-service.jar"]