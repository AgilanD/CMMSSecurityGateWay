package com.example.gateway.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MasterData", url = "http://localhost:8083/MasterData")
public interface UserFeignClientMasterData {


    @GetMapping("/Checking")
    public String Checkings();



}
