package com.example.gateway.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "Logistics", url = "http://localhost:8082/Logistics")
public interface UserFeignClientLogistics {

    @GetMapping("/checking")
    public String Checkings();

}
