package com.example.gateway.clients;


import com.example.gateway.common.dto.*;
import com.example.gateway.config.FeignClientInterceptorConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Production", url = "http://localhost:8084/Production",configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClientProduction {


    @GetMapping("/Checking")
    public String checkingMessage();

    @PostMapping("/AddQualityInspectionResponse")
    public QualityInspectionResponseDto create(@RequestBody QualityInspectionRequestDto request);

    @GetMapping("/GetById/{id}")
    public QualityInspectionResponseDto getById(@PathVariable Long id);

    @GetMapping("/GetAllQualityInspection")
    public List<QualityInspectionResponseDto> getAll();
    @PutMapping("/UpdateById/{id}")
    public QualityInspectionResponseDto update(@PathVariable Long id, @RequestBody QualityInspectionRequestDto request);

    @DeleteMapping("/DeleteById/{id}")
    public void delete(@PathVariable Long id);

    @PostMapping("/AddVehiclesinVENTED")
    public VehicleInventoryResponseDto creates(@Valid @RequestBody VehicleInventoryRequestDto requestDto);

    @GetMapping("/GetById/{id}")
    public VehicleInventoryResponseDto getByIds(@PathVariable Long id);
    @GetMapping("/GetAllVehicle")
    public List<VehicleInventoryResponseDto> getAlls();

    @PutMapping("/AddVehicle/{id}")
    public VehicleInventoryResponseDto updates(@PathVariable Long id, @Valid @RequestBody VehicleInventoryRequestDto requestDto);

    @DeleteMapping("/AddVehicle/{id}")
    public void deleteByIds(@PathVariable Long id);



    @PostMapping("/Productionsorders")
    public ProductionOrderResponseDto createOrder(@Valid @RequestBody ProductionOrderRequestDto requestDto);

    @GetMapping("/Productionsorders/{id}")
    public ProductionOrderResponseDto getOrderById(@PathVariable Long id);

    @GetMapping("/Productionsorders")
    public List<ProductionOrderResponseDto> getAllOrders();

    @PutMapping("/Productionsorders/{id}")
    public ProductionOrderResponseDto updateOrder(@PathVariable Long id, @Valid @RequestBody ProductionOrderRequestDto requestDto);

    @DeleteMapping("/Productionsorders/{id}")
    public void deleteOrder(@PathVariable Long id);

}
