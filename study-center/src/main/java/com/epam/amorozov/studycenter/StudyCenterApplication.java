package com.epam.amorozov.studycenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StudyCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCenterApplication.class, args);
    }

}
