package com.example.gateway.clients;

import com.example.gateway.common.dto.*;
import com.example.gateway.config.FeignClientInterceptorConfig;
import com.example.gateway.exception.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "HumanResource", url = "http://localhost:8081/human", configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClient {

    @GetMapping("/AllEmployee")
    public ResponseEntity<APIResponse<List<EmployeeResponseDto>>> getDetails();

    @GetMapping("/employee/{id}")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable("id") Long id);

    @PostMapping("/addEmployee")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> postMappings(@RequestBody EmployeeRequestDto requestDto);

    @PutMapping("/updateEmployeeByIds/{id}")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody EmployeeRequestDto requestDto);

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<APIResponse<String>> softDeleteEmployee(@PathVariable("id") Long id);

    @GetMapping("/recycleBin")
    public ResponseEntity<APIResponse<List<EmployeeResponseDto>>> getRecycleBin();

    @PostMapping("/addCustomer")
    public ResponseEntity<APIResponse<CustomerResponseDto>> addCustomer(@RequestBody CustomerRequestDto requestDto);

    @GetMapping("/allCustomers")
    public ResponseEntity<APIResponse<List<CustomerResponseDto>>> getAllCustomers();

    @GetMapping("/customer/{id}")
    public ResponseEntity<APIResponse<CustomerResponseDto>> getCustomerById(@PathVariable("id") Long id);

    @PutMapping("/updateCustomerByIds/{id}")
    public ResponseEntity<APIResponse<CustomerResponseDto>> updateCustomer(
            @PathVariable("id") Long id,
            @RequestBody CustomerRequestDto requestDto);

    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<APIResponse<String>> deleteCustomer(@PathVariable("id") Long id);
}
