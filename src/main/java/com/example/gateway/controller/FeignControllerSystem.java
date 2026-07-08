package com.example.gateway.controller;

import com.example.gateway.aspect.AuditLoggable;
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


    @GetMapping("/System/GetAllAuditLogs")
    public List<AuditLogs> getAllAuditLogs(){
        return userFeignClientSystem.getAllAuditLogs();
    }


    @PostMapping("/System/AddAuditLogs")
    public AuditLogResponseDto createAuditLog(@RequestBody AuditLogsRequestDto requestDto) {
        return userFeignClientSystem.createAuditLog(requestDto);
    }

    @GetMapping("/System/getAuditLogByIds/{id}")
    public AuditLogResponseDto getAuditLogUsingIds(@PathVariable Long id){
        return userFeignClientSystem.getAuditLogUsingIds(id);
    }

    @PostMapping("/System/CreateHistory")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "service_history")
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
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "service_history")
    public ServiceHistoryResponseDto updateHistory(
            @PathVariable Long id,
            @RequestBody ServiceHistoryRequestDto requestDto) {

        return userFeignClientSystem.updateHistory(id,requestDto);
    }

    @DeleteMapping("/System/deleteHistory/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "service_history")
    public void deleteHistory(@PathVariable Long id) {
        userFeignClientSystem.deleteHistory(id);
    }

}
