FROM openjdk:11.0.12-jre-buster
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} study-center.jar
ENTRYPOINT ["java","-jar","study-center.jar"]