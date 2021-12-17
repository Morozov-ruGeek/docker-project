FROM openjdk:11.0.12-jre-buster
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} payment-service.jar
CMD ["java","-jar","payment-service.jar"]