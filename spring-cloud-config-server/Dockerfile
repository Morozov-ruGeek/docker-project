FROM openjdk:11.0.12-jre-buster
VOLUME /tmp
ARG JAR_FILE=build/libs/spring-cloud-config.jar
COPY ${JAR_FILE} spring-cloud-config.jar
ENTRYPOINT ["java","-jar","spring-cloud-config.jar"]