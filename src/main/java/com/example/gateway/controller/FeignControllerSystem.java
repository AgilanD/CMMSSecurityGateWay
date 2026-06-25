package com.example.gateway.controller;

import com.example.gateway.clients.UserFeignClientSystem;
import com.example.gateway.common.entity.AuditLogs;
import com.example.gateway.common.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignControllerSystem {

    private final UserFeignClientSystem userFeignClientSystem;


    @GetMapping("/System/checkings")
    public String Message(){
        return userFeignClientSystem.Checkings();
    }


    @GetMapping("/System/GetAllAuditLogs")
    public List<AuditLogs> GetAllAuditLogs(){
        return userFeignClientSystem.GetAllAuditLogs();
    }


    @PostMapping("/System/AddAuditLogs")
    public AuditLogResponseDto createAuditLog(@RequestBody AuditLogsRequestDto requestDto) {
        return userFeignClientSystem.createAuditLog(requestDto);
    }



    @PostMapping("/System/CreateOrder")
    public ProductionOrderResponseDto createOrder(@RequestBody ProductionOrderRequestDto requestDto) {
        return userFeignClientSystem.createOrder(requestDto);
    }

    @GetMapping("" +
            "")
    public List<ProductionOrderResponseDto> getAllOrders() {
        return userFeignClientSystem.getAllOrders();
    }

    @GetMapping("/System/GetOrderByid/{id}")
    public ProductionOrderResponseDto getOrderById(@PathVariable Long id) {
       return userFeignClientSystem.getOrderById(id);
    }

    @PutMapping("/System/UpdateOrder/{id}")
    public ProductionOrderResponseDto updateOrder(@PathVariable Long id, @RequestBody ProductionOrderRequestDto requestDto) {
       return userFeignClientSystem.updateOrder(requestDto);
    }

    @DeleteMapping("/System/DeleteOrder/{id}")
    public void deleteOrder(@PathVariable Long id) {
        userFeignClientSystem.deleteOrder(id);
    }





    @PostMapping("/System/CreateHistory")
    public ServiceHistoryResponseDto createHistory(@RequestBody ServiceHistoryRequestDto requestDto) {
        return userFeignClientSystem.createHistory(requestDto);
    }

    @GetMapping("/System/GetAllHistory")
    public List<ServiceHistoryResponseDto> getAllHistories() {
        return userFeignClientSystem.getAllHistories();
    }

    @GetMapping("/System/GetHistoryById/{id}")
    public ServiceHistoryResponseDto getHistoryById(@PathVariable Long id) {
        return userFeignClientSystem.getHistoryById(id);
    }

    @PutMapping("/SystemUpdateHistoryById/{id}")
    public ServiceHistoryResponseDto updateHistory(
            @PathVariable Long id,
            @RequestBody ServiceHistoryRequestDto requestDto) {

        return userFeignClientSystem.updateHistory(id,requestDto);
    }

    @DeleteMapping("/System/deleteHistory/{id}")
    public void deleteHistory(@PathVariable Long id) {
        userFeignClientSystem.deleteHistory(id);
    }

}
