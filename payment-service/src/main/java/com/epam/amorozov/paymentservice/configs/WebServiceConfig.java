package com.epam.amorozov.paymentservice.configs;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    private static final String SERVLET_URL_MAPPINGS = "/api/v1/soap/*";
    private static final String PORT_TYPE_NAME = "PaymentPort";
    private static final String LOCATION_PAYMENT_URI = "/api/v1/soap";
    private static final String PAYMENT_NAME_SPACE = "http://www.amorozov.com/spring/ws/payments";
    private static final String PAYMENT_SCHEMA_PATH = "xsd/payment.xsd";

    @Bean
    @LoadBalanced
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, SERVLET_URL_MAPPINGS);
    }

    // http://localhost:8189/api/v1/soap/payment.wsdl
    @Bean(name = "payment")
    public DefaultWsdl11Definition studentsWsdl11Definition(XsdSchema paymentSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(PORT_TYPE_NAME);
        wsdl11Definition.setLocationUri(LOCATION_PAYMENT_URI);
        wsdl11Definition.setTargetNamespace(PAYMENT_NAME_SPACE);
        wsdl11Definition.setSchema(paymentSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema paymentSchema() {
        return new SimpleXsdSchema(new ClassPathResource(PAYMENT_SCHEMA_PATH));
    }
}