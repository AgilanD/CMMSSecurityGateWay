package com.example.gateway.controller;


import com.example.gateway.clients.UserFeignClientLogistics;
import com.example.gateway.common.dto.VehicalDeliveryRequestDto;
import com.example.gateway.common.dto.VehicalDeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeignControllerLogistics {

    private final UserFeignClientLogistics userFeignClientLogistics;

    @GetMapping("/Logistics/checking")
    public String fetchExternalUser() {
        return userFeignClientLogistics.checkingMessage();
    }

    @PostMapping("/Logistics/AddDelivery")
    public VehicalDeliveryResponseDto createVehical(@RequestBody VehicalDeliveryRequestDto requestDto) {
        return  userFeignClientLogistics.createVehical(requestDto);
    }

    @GetMapping("/Logistics/GetById/{id}")
    public VehicalDeliveryResponseDto getByIdVehical(@PathVariable Long id) {
        return  userFeignClientLogistics.getByIdVehical(id);
    }

    @GetMapping("/Logistics/GetAllDeliveries")
    public List<VehicalDeliveryResponseDto> getAllVehical() {
        return  userFeignClientLogistics.getAllVehical();
    }

    @PutMapping("/Logistics/UpdateByIdVehicals/{id}")
    public VehicalDeliveryResponseDto updateVehical(
            @PathVariable Long id,
            @RequestBody VehicalDeliveryRequestDto requestDto) {
        log.info("Hello Bye");
        return  userFeignClientLogistics.updateVehical(id,requestDto);
    }

    @DeleteMapping("/Logistics/DeleteById/{id}")
    public void deleteVehical(@PathVariable Long id) {
        userFeignClientLogistics.deleteVehical(id);
    }

}
