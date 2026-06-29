package com.example.gateway.controller;

import com.example.gateway.clients.UserFeignClientProduction;
import com.example.gateway.common.dto.QualityInspectionRequestDto;
import com.example.gateway.common.dto.QualityInspectionResponseDto;
import com.example.gateway.common.dto.VehicleInventoryRequestDto;
import com.example.gateway.common.dto.VehicleInventoryResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignControllerProduction {

    private final UserFeignClientProduction userFeignClientProduction;

    @GetMapping("/Production/Checking")
    public String Message(){
        return userFeignClientProduction.Checkings();
    }

    @PostMapping("/Production/AddQualityInspectionResponse")
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
    public QualityInspectionResponseDto update(@PathVariable Long id, @RequestBody QualityInspectionRequestDto request){
        return userFeignClientProduction.update(id, request);
    }

    @DeleteMapping("/Production/DeleteById/{id}")
    public void delete(@PathVariable Long id) {
        userFeignClientProduction.delete(id);
    }


    @PostMapping("/Production/AddVehicles")
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
    public VehicleInventoryResponseDto updates(
            @PathVariable Long id,
            @Valid @RequestBody VehicleInventoryRequestDto requestDto) {
        return userFeignClientProduction.updates(id, requestDto);
    }

    @DeleteMapping("/Production/AddVehicle/{id}")
    public void deleteByIds(@PathVariable Long id) {
        userFeignClientProduction.deleteByIds(id);
    }

}
