package com.example.gateway.controller;

import com.example.gateway.aspect.AuditLoggable;
import com.example.gateway.common.dto.*;
import com.example.gateway.clients.UserFeignClient;
import com.example.gateway.exception.APIResponse;
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
    public ResponseEntity<APIResponse<List<EmployeeResponseDto>>> fetchExternalUser() {
        return userfeignClient.getDetails();
    }

    @GetMapping("/human/employee/{id}")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable Long id) {
        return userfeignClient.getEmployeeById(id);
    }

    @PostMapping("/human/addEmployee")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "employee")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> fetchData(@RequestBody EmployeeRequestDto requestDto){
        return userfeignClient.postMappings(requestDto);
    }

    @PutMapping("/human/updateEmployeeByIds/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "employee")
    public ResponseEntity<APIResponse<EmployeeResponseDto>> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDto requestDto) {
        return userfeignClient.updateEmployee(id, requestDto);
    }

    @DeleteMapping("/human/deleteEmployee/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "employee")
    public ResponseEntity<APIResponse<String>> softDeleteEmployee(@PathVariable Long id) {
        return userfeignClient.softDeleteEmployee(id);
    }

    @GetMapping("/human/recycleBin")
    public ResponseEntity<APIResponse<List<EmployeeResponseDto>>> getRecycleBin() {
        return userfeignClient.getRecycleBin();
    }

    @PostMapping("/human/addCustomer")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "customers")
    public ResponseEntity<APIResponse<CustomerResponseDto>> addCustomer(@RequestBody CustomerRequestDto requestDto) {
        return userfeignClient.addCustomer(requestDto);
    }

    @GetMapping("/human/allCustomers")
    public ResponseEntity<APIResponse<List<CustomerResponseDto>>> getAllCustomers() {
        return userfeignClient.getAllCustomers();
    }

    @GetMapping("/human/customer/{id}")
    public ResponseEntity<APIResponse<CustomerResponseDto>> getCustomerById(@PathVariable Long id) {
        return userfeignClient.getCustomerById(id);
    }

    @PutMapping("/human/updateCustomerByIds/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "customers")
    public ResponseEntity<APIResponse<CustomerResponseDto>> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerRequestDto requestDto) {
        return userfeignClient.updateCustomer(id, requestDto);
    }

    @DeleteMapping("/human/deleteCustomer/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "customers")
    public ResponseEntity<APIResponse<String>> deleteCustomer(@PathVariable Long id) {
        return userfeignClient.deleteCustomer(id);
    }
}
