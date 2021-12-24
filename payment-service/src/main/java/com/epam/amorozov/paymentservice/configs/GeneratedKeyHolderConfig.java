package com.epam.amorozov.paymentservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.GeneratedKeyHolder;

@Configuration
public class GeneratedKeyHolderConfig {

    @Bean
    public GeneratedKeyHolder createBeanKeyHolder() {
        return new GeneratedKeyHolder();
    }
}
