package com.example.gateway.controller;


import com.example.gateway.clients.UserFeignClientMasterData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeignControllerMasterData {

    private final UserFeignClientMasterData userfeignClientMasterData;


    @GetMapping("/MasterData/Checking")
    public String checking(){
        return userfeignClientMasterData.Checkings();
    }

}
