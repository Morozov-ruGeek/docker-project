package com.epam.amorozov.studycenter.configs;

import com.epam.amorozov.studycenter.soap.client.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PaymentClientConfig {

    @Value("${soap.payment-url}")
    String paymentUrl;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.epam.amorozov.studycenter.soap.model");
        return marshaller;
    }

    @Bean
    public PaymentClient paymentClient(Jaxb2Marshaller marshaller) {
        PaymentClient paymentClient = new PaymentClient();
        paymentClient.setDefaultUri(paymentUrl);
        paymentClient.setMarshaller(marshaller);
        paymentClient.setUnmarshaller(marshaller);
        return paymentClient;
    }
}
