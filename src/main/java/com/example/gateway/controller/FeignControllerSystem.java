package com.example.gateway.controller;

import com.example.gateway.clients.UserFeignClientSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignControllerSystem {

    private final UserFeignClientSystem userFeignClientSystem;


    @GetMapping("/System/checkings")
    public String Message(){
        return userFeignClientSystem.Checkings();
    }


}
