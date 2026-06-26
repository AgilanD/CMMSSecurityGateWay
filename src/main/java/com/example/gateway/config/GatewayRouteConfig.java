//package com.example.gateway.config;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GatewayRouteConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                // 1. Intercept anything coming to Gateway port 8080 under /api/v1/production-route/**
//                .route("production_service_route", r -> r.path("/api/v1/production-route/**")
//                        .filters(f -> f
//                                // 2. REWRITE RULE: Safely maps the gateway path to your real controller prefix /Production/
//                                .rewritePath("/api/v1/production-route/(.*)", "/Production/$1")
//                        )
//                        // 3. Forwards the request along with the browser cookies directly to port 8081
//                        .uri("http://localhost:8081"))
//                .build();
//    }
//}
