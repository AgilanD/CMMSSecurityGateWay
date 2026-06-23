package com.example.gateway.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Production", url = "http://localhost:8084/Production")
public interface UserFeignClientProduction {


    @GetMapping("/Checking")
    public String Checkings();

}
