FROM openjdk:11.0.12-jre-buster
RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*
VOLUME /tmp
ARG JAR_FILE=build/libs/study-center.jar
COPY ${JAR_FILE} study-center.jar
ENTRYPOINT ["java","-jar","study-center.jar"]