package com.example.gateway.clients;

import com.example.gateway.common.entity.AuditLogs;
import com.example.gateway.common.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "System", url = "http://localhost:8085/System")
public interface UserFeignClientSystem {

    @GetMapping("/checkings")
    public String Checkings();

    @GetMapping("/GetAllAuditLogs")
    public List<AuditLogs> GetAllAuditLogs();


    @PostMapping("/AddAuditLogs")
    public AuditLogResponseDto createAuditLog(@RequestBody AuditLogsRequestDto requestDto);



    @PostMapping("/CreateOrder")
    public ProductionOrderResponseDto createOrder(@RequestBody ProductionOrderRequestDto requestDto);

    @GetMapping("/GetAllOrder")
    public List<ProductionOrderResponseDto> getAllOrders();

    @GetMapping("/GetOrderByid/{id}")
    public ProductionOrderResponseDto getOrderById(@PathVariable Long id);

    @PutMapping("/UpdateOrder/{id}")
    public ProductionOrderResponseDto updateOrder(
            @RequestBody ProductionOrderRequestDto requestDto);

    @DeleteMapping("/DeleteOrder/{id}")
    public void deleteOrder(@PathVariable Long id);



    @PostMapping("/CreateHistory")
    public ServiceHistoryResponseDto createHistory(@RequestBody ServiceHistoryRequestDto requestDto);

    @GetMapping("/GetAllHistory")
    public List<ServiceHistoryResponseDto> getAllHistories();

    @GetMapping("/GetHistoryById/{id}")
    public ServiceHistoryResponseDto getHistoryById(@PathVariable Long id);

    @PutMapping("UpdateHistoryById/{id}")
    public ServiceHistoryResponseDto updateHistory(
            @PathVariable Long id,
            @RequestBody ServiceHistoryRequestDto requestDto);

    @DeleteMapping("/deleteHistory/{id}")
    public void deleteHistory(@PathVariable Long id);


}
