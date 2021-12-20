FROM openjdk:11.0.12-jre-buster
VOLUME /tmp
COPY build/libs/payment-service.jar payment-service.jar
ENTRYPOINT ["java","-jar","payment-service.jar"]