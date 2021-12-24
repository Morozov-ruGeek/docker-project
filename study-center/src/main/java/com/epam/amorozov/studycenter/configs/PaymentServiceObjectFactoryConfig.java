package com.epam.amorozov.studycenter.configs;

import com.epam.amorozov.studycenter.soap.model.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceObjectFactoryConfig {
    @Bean
    public ObjectFactory createObjectFactory() {
        return new ObjectFactory();
    }
}
