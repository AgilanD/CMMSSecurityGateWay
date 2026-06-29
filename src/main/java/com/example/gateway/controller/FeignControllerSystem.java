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
