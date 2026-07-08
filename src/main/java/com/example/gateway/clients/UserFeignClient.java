package com.example.gateway.clients;

import com.example.gateway.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "HumanResource", url = "http://localhost:8081/human")
public interface UserFeignClient {

    @GetMapping("/AllEmployee")
    public ResponseEntity<List<EmployeeResponseDto>> getDetails();

    @PostMapping("/addEmployee")

    public ResponseEntity<EmployeeResponseDto> postMappings(@RequestBody EmployeeRequestDto requestDto);

    @DeleteMapping("/deleteEmployee/{id}")
    public String softDeleteEmployee(@PathVariable Long id);

    @GetMapping("/recycleBin")
    public List<EmployeeResponseDto> getRecycleBin();

    @PostMapping("/addCustomer")
    public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto requestDto);

    @GetMapping("/allCustomers")
    public List<CustomerResponseDto> getAllCustomers();

    @GetMapping("/customer/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable Long id);

    @PutMapping("/updateCustomerByIds/{id}")
    public CustomerResponseDto updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDto requestDto);

    @DeleteMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable Long id);

}
