package com.epam.amorozov.spring.cloud.starter.netflix.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudStarterNetflixEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStarterNetflixEurekaServerApplication.class, args);
    }

}
