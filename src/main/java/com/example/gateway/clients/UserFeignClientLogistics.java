package com.example.gateway.clients;


import com.example.gateway.common.dto.NotificationsRequestDto;
import com.example.gateway.common.dto.NotificationsResponseDto;
import com.example.gateway.common.dto.VehicalDeliveryRequestDto;
import com.example.gateway.common.dto.VehicalDeliveryResponseDto;
import com.example.gateway.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Logistics", url = "http://localhost:8082/Logistics",configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClientLogistics {

    @GetMapping("/checking")
    public String checkingMessage();

    @PostMapping("/AddDelivery")
    public VehicalDeliveryResponseDto createVehical(@RequestBody VehicalDeliveryRequestDto requestDto);
    @GetMapping("/GetByIdVehicals/{id}")
    public VehicalDeliveryResponseDto getByIdVehical(@PathVariable Long id);

    @GetMapping("/GetAllDeliveries")
    public List<VehicalDeliveryResponseDto> getAllVehical();

    @PutMapping("/UpdateByIdVehicals/{id}")
    public VehicalDeliveryResponseDto updateVehical(@PathVariable Long  id, @RequestBody VehicalDeliveryRequestDto requestDto);

    @DeleteMapping("/DeleteByIdVehicals/{id}")
    public void deleteVehical(@PathVariable Long id);

    @PostMapping("/AddNotification")
    public NotificationsResponseDto createNotifications(@RequestBody NotificationsRequestDto requestDto);

    @GetMapping("/GetNotificationById/{id}")
    public NotificationsResponseDto notificationsgetById(@PathVariable Long id);

    @GetMapping("/GetAllNotifications")
    public List<NotificationsResponseDto> getAllNotifications();

    @PutMapping("/UpdateNotificationById/{id}")
    public NotificationsResponseDto updateNotifications(
            @PathVariable Long id,
            @RequestBody NotificationsRequestDto requestDto);

    @PatchMapping("/MarkAsRead/{id}")
    public NotificationsResponseDto markAsReadNotifications(@PathVariable Long id) ;

    @DeleteMapping("/DeleteNotificationById/{id}")
    public void delete(@PathVariable Long id);

}
