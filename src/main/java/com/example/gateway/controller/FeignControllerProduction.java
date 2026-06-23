package com.example.gateway.controller;

import com.example.gateway.clients.UserFeignClientProduction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignControllerProduction {

    private final UserFeignClientProduction userFeignClientProduction;

    @GetMapping("/Production/Checking")
    public String Message(){
        return userFeignClientProduction.Checkings();
    }


}
