package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SecurityGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityGatewayApplication.class,args);
    }

}
