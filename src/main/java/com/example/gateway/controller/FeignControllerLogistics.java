package com.example.gateway.controller;


import com.example.gateway.clients.UserFeignClientLogistics;
import com.example.gateway.common.dto.VehicalDeliveryRequestDto;
import com.example.gateway.common.dto.VehicalDeliveryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignControllerLogistics {

    private final UserFeignClientLogistics userFeignClientLogistics;

    @GetMapping("/Logistics/checking")
    public String fetchExternalUser() {
        return userFeignClientLogistics.Checkings();
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

    @PutMapping("/Logistics/UpdateById/{id}")
    public VehicalDeliveryResponseDto updateVehical(
            @PathVariable Long id,
            @RequestBody VehicalDeliveryRequestDto requestDto) {
        return  userFeignClientLogistics.updateVehical(id,requestDto);
    }

    @DeleteMapping("/Logistics/DeleteById/{id}")
    public void deleteVehical(@PathVariable Long id) {
        userFeignClientLogistics.deleteVehical(id);
    }

}
