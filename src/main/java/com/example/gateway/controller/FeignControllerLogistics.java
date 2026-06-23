package com.example.gateway.controller;


import com.example.gateway.clients.UserFeignClientLogistics;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignControllerLogistics {

    private final UserFeignClientLogistics userFeignClientLogistics;

    @GetMapping("/Logistics/checking")
    public String fetchExternalUser() {

        return userFeignClientLogistics.Checkings();

    }

}
