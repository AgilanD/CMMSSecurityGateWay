package com.example.gateway.controller;


import cmms.HumanResource.Dto.CustomerRequestDto;
import cmms.HumanResource.Dto.CustomerResponseDto;
import com.example.gateway.Dto.EmployeeRequestDto;
import com.example.gateway.Dto.EmployeeResponseDto;
import com.example.gateway.clients.UserFeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class FeignController {

    private final UserFeignClient userfeignClient;


    public FeignController(UserFeignClient userfeignClient) {
        this.userfeignClient = userfeignClient;
    }

    @GetMapping("/human/All")
    public ResponseEntity<?> fetchExternalUser() {

        return userfeignClient.getDetails();

    }

    @PostMapping("/human/addEmployee")
    public ResponseEntity<EmployeeResponseDto>  ferchData(@RequestBody EmployeeRequestDto requestDto){
        return userfeignClient.Postmappings(requestDto);
    }

    @GetMapping("/human/AllCustomers")
    public ResponseEntity<?> AllCustomers(){
        return userfeignClient.AllCustomers();
    }

    @PostMapping("/human/AddCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        return userfeignClient.addCustomer(customerRequestDto);
    }

}
