package com.epam.amorozov.studycenter.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SpringFoxSwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.epam.module.six.spring.boot.controllers"))
                .paths(PathSelectors.ant("/students/*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Application REST API",
                "Beta API.",
                "/api/v1",
                "http://localhost:8190/study_center",
                new Contact("Aleksei Morozov", "www.example.com", "morozov4811@gmail.com"),
                "License of API", "API license URL", Collections.emptyList());
    }
}

