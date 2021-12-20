FROM openjdk:11.0.12-jre-buster
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} payment-service.jar
EXPOSE 8189
ENTRYPOINT ["java","-jar","payment-service.jar"]