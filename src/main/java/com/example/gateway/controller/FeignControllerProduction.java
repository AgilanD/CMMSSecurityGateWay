package com.example.gateway.controller;

import com.example.gateway.aspect.AuditLoggable;
import com.example.gateway.aspect.TrackNotification;
import com.example.gateway.clients.UserFeignClientProduction;
import com.example.gateway.common.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeignControllerProduction {

    private final UserFeignClientProduction userFeignClientProduction;

    @GetMapping("/Production/Checking")
    public String checkingMessage(){
        return userFeignClientProduction.checkingMessage();
    }

    @PostMapping("/Production/AddQualityInspectionResponse")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "production_orders")
    public QualityInspectionResponseDto create(@RequestBody QualityInspectionRequestDto request) {
        return userFeignClientProduction.create(request);
    }

    @GetMapping("/Production/GetById/{id}")
    public QualityInspectionResponseDto getById(@PathVariable Long id) {
        return userFeignClientProduction.getById(id);
    }

    @GetMapping("/Production/GetAllQualityInspection")
    public List<QualityInspectionResponseDto> getAll() {
        return userFeignClientProduction.getAll();
    }

    @PutMapping("/Production/UpdateById/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "quality_inspections")
    @TrackNotification(type = NotificationsRequestDto.NotificationType.QC_FAIL)
    public QualityInspectionResponseDto update(@PathVariable Long id, @RequestBody QualityInspectionRequestDto request){
        return userFeignClientProduction.update(id, request);
    }

    @DeleteMapping("/Production/DeleteById/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "production_orders")
    public void delete(@PathVariable Long id) {
        userFeignClientProduction.delete(id);
    }

    @PostMapping("/Production/AddVehiclesinVENTED")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "vehicals")
    public VehicleInventoryResponseDto creates(@Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return userFeignClientProduction.creates(requestDto);
    }

    @GetMapping("/Production/GetByIds/{id}")
    public VehicleInventoryResponseDto getByIds(@PathVariable Long id) {
        return userFeignClientProduction.getByIds(id);
    }

    @GetMapping("/Production/GetAllVehicle")
    public List<VehicleInventoryResponseDto> getAlls() {
        return userFeignClientProduction.getAlls();
    }

    @PutMapping("/Production/AddVehicle/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "vehicals")
    public VehicleInventoryResponseDto updates(
            @PathVariable Long id,
            @Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return userFeignClientProduction.updates(id, requestDto);
    }

    @DeleteMapping("/Production/AddVehicle/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "vehicals")
    public void deleteByIds(@PathVariable Long id) {
        userFeignClientProduction.deleteByIds(id);
    }

    @PostMapping("/Production/Productionsorders")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "production_orders")
    public ProductionOrderResponseDto createOrder(@Valid @RequestBody ProductionOrderRequestDto requestDto) {
        return userFeignClientProduction.createOrder(requestDto);
    }

    @GetMapping("/Production/Productionsorders/{id}")
    public ProductionOrderResponseDto getOrderById(@PathVariable Long id) {
        return userFeignClientProduction.getOrderById(id);
    }

    @GetMapping("/Production/Productionsorders")
    public List<ProductionOrderResponseDto> getAllOrders() {
        return userFeignClientProduction.getAllOrders();
    }

    @PutMapping("/Production/Productionsorders/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "production_orders")
    @TrackNotification(type = NotificationsRequestDto.NotificationType.STATUS_CHANGE)
    public ProductionOrderResponseDto updateOrder(@PathVariable Long id, @Valid @RequestBody ProductionOrderRequestDto requestDto) {
        return userFeignClientProduction.updateOrder(id, requestDto);
    }

    @DeleteMapping("/Production/Productionsorders/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "production_orders")
    public void deleteOrder(@PathVariable Long id) {
        userFeignClientProduction.deleteOrder(id);
    }

}
