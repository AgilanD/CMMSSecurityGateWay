package com.example.gateway.clients;


import cmms.HumanResource.Dto.CustomerRequestDto;
import cmms.HumanResource.Dto.CustomerResponseDto;
import com.example.gateway.Dto.EmployeeRequestDto;
import com.example.gateway.Dto.EmployeeResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

// Connects directly to the specified base URL
@FeignClient(name = "HumanResource", url = "http://localhost:8081/human")
public interface UserFeignClient {

    @GetMapping("/All")
    public ResponseEntity<?> getDetails();


    @PostMapping("/addEmployee")
    public ResponseEntity<EmployeeResponseDto> Postmappings(@RequestBody EmployeeRequestDto requestDto);


    @GetMapping("/AllCustomers")
    public ResponseEntity<?> AllCustomers();

    @PostMapping("/AddCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody CustomerRequestDto customerRequestDto);

}
