package com.trangle.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author trangle
 */
@Configuration
public class GatewayConfig {

//    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("pay-route",r -> r.path("/pay/**").uri("lb://pay"))
                .route("order-route",r -> r.path("/order/**").uri("lb://order"))
                .build();
    }
}
