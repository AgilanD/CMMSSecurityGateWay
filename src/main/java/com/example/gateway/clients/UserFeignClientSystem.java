package com.example.gateway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "System", url = "http://localhost:8085/System")
public interface UserFeignClientSystem {

    @GetMapping("/checkings")
    public String Checkings();

}
