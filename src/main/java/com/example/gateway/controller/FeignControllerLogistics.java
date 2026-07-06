package com.example.gateway.controller;


import com.example.gateway.clients.UserFeignClientLogistics;
import com.example.gateway.common.dto.NotificationsRequestDto;
import com.example.gateway.common.dto.NotificationsResponseDto;
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

    @GetMapping("/Logistics/GetByIdVehicals/{id}")
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
        return  userFeignClientLogistics.updateVehical(id,requestDto);
    }

    @DeleteMapping("/Logistics/DeleteByIdVehicals/{id}")
    public void deleteVehical(@PathVariable Long id) {
        userFeignClientLogistics.deleteVehical(id);
    }

    @PostMapping("/Logistics/AddNotification")
    public NotificationsResponseDto createNotifications(@RequestBody NotificationsRequestDto requestDto) {
        return userFeignClientLogistics.createNotifications(requestDto);
    }

    @GetMapping("/Logistics/GetNotificationById/{id}")
    public NotificationsResponseDto notificationsgetById(@PathVariable Long id) {
        return userFeignClientLogistics.notificationsgetById(id);
    }

    @GetMapping("/Logistics/GetAllNotifications")
    public List<NotificationsResponseDto> getAllNotifications() {
        return userFeignClientLogistics.getAllNotifications();
    }

    @PutMapping("/Logistics/UpdateNotificationById/{id}")
    public NotificationsResponseDto updateNotifications(
            @PathVariable Long id,
            @RequestBody NotificationsRequestDto requestDto) {
        return userFeignClientLogistics.updateNotifications(id, requestDto);
    }

    @PatchMapping("/Logistics/MarkAsRead/{id}")
    public NotificationsResponseDto markAsReadNotifications(@PathVariable Long id) {
        return userFeignClientLogistics.markAsReadNotifications(id);
    }

    @DeleteMapping("/Logistics/DeleteNotificationById/{id}")
    public void delete(@PathVariable Long id) {
        userFeignClientLogistics.delete(id);
    }


}
