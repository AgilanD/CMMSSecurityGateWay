package com.example.gateway.clients;


import com.example.gateway.common.dto.VehicalDeliveryRequestDto;
import com.example.gateway.common.dto.VehicalDeliveryResponseDto;
import com.example.gateway.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Logistics", url = "http://localhost:8082/Logistics",configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClientLogistics {

    @GetMapping("/checking")
    public String Checkings();

    @PostMapping("/AddDelivery")
    public VehicalDeliveryResponseDto createVehical(@RequestBody VehicalDeliveryRequestDto requestDto);
    @GetMapping("/GetById/{id}")
    public VehicalDeliveryResponseDto getByIdVehical(@PathVariable Long id);

    @GetMapping("/GetAllDeliveries")
    public List<VehicalDeliveryResponseDto> getAllVehical();

    @PutMapping("/UpdateById/{id}")
    public VehicalDeliveryResponseDto updateVehical(@PathVariable Long id, @RequestBody VehicalDeliveryRequestDto requestDto);

    @DeleteMapping("/DeleteById/{id}")
    public void deleteVehical(@PathVariable Long id);

}
