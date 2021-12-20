FROM openjdk:11.0.12-jre-buster
VOLUME /tmp
COPY payment-service.jar payment-service.jar
EXPOSE 8189
ENTRYPOINT ["java","-jar","payment-service.jar"]