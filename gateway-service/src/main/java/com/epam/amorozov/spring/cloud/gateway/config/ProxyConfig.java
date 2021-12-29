package com.epam.amorozov.spring.cloud.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

//@Configuration
public class ProxyConfig {

    @Value("${study-center.name}")
    private String studentServiceRouteId;

    @Value("${study-center.path}")
    private String studentServicePath;

    @Value("${study-center.uri}")
    private String studentServiceUri;

    @Value("${payment-service.name}")
    private String paymentServiceRouteId;

    @Value("${payment-service.path}")
    private String paymentServicePath;

    @Value("${payment-service.uri}")
    private String paymentServiceUri;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(studentServiceRouteId,
                        route -> route.path(studentServicePath)
                                .and()
                                .method(HttpMethod.GET)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri(studentServiceUri))
                .route(paymentServiceRouteId,
                        route -> route.path(paymentServicePath)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri(paymentServiceUri))
                .build();
    }
}