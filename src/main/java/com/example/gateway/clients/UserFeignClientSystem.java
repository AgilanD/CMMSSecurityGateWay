package com.example.gateway.clients;

import com.example.gateway.common.entity.AuditLogs;
import com.example.gateway.common.dto.*;
import com.example.gateway.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "System", url = "http://localhost:8085/System",configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClientSystem {


    @GetMapping("/GetAllAuditLogs")
    public List<AuditLogs> getAllAuditLogs();

    @PostMapping("/AddAuditLogs")
    public AuditLogResponseDto createAuditLog(@RequestBody AuditLogsRequestDto requestDto);

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
