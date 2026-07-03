package com.example.gateway.controller;

import com.example.gateway.common.dto.EmployeeRequestDto;
import com.example.gateway.common.dto.EmployeeResponseDto;
import com.example.gateway.clients.UserFeignClient;
import com.example.gateway.common.dto.CustomerRequestDto;
import com.example.gateway.common.dto.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class FeignController {

    private final UserFeignClient userfeignClient;


    public FeignController(UserFeignClient userfeignClient) {
        this.userfeignClient = userfeignClient;
    }

    @GetMapping("/human/AllEmployee")
    public ResponseEntity<List<EmployeeResponseDto>> fetchExternalUser() {
        return userfeignClient.getDetails();
    }

    @PostMapping("/human/addEmployee")
    public ResponseEntity<EmployeeResponseDto>  fetchData(@RequestBody EmployeeRequestDto requestDto){
        return userfeignClient.postMappings(requestDto);
    }

    @DeleteMapping("/human/deleteEmployee/{id}")
    public String softDeleteEmployee(@PathVariable Long id) {
        userfeignClient.softDeleteEmployee(id);
        return "Employee soft-deleted successfully with ID: " + id;
    }

    @GetMapping("/human/recycleBin")
    public List<EmployeeResponseDto> getRecycleBin() {
        return userfeignClient.getRecycleBin();
    }

    @PostMapping("/human/addCustomer")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto requestDto) {
        return userfeignClient.addCustomer(requestDto);
    }

    @GetMapping("/human/allCustomers")
    public List<CustomerResponseDto> getAllCustomers() {
        return userfeignClient.getAllCustomers();
    }

    @GetMapping("/human/customer/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable Long id) {
        return userfeignClient.getCustomerById(id);
    }

    @PutMapping("/human/updateCustomer/{id}")
    public CustomerResponseDto updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerRequestDto requestDto) {

        return userfeignClient.updateCustomer(id, requestDto);
    }

    @DeleteMapping("/human/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        userfeignClient.deleteCustomer(id);
        return "Customer deleted successfully with ID: " + id;
    }

}
