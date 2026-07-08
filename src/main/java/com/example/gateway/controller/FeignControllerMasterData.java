package com.example.gateway.controller;

import com.example.gateway.aspect.AuditLoggable;
import com.example.gateway.common.dto.*;
import com.example.gateway.clients.UserFeignClientMasterData;
import com.example.gateway.common.entity.SupplierRequestDto;
import com.example.gateway.common.entity.Suppliers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignControllerMasterData {

    private final UserFeignClientMasterData userfeignClientMasterData;

    @PostMapping("/MasterData/AddCarModel")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "car_module")
    public CarModuleResponseDto  addCarModule(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto){
        return userfeignClientMasterData. addCarModule(carModuleRequestDtoRequestDto);
    }

    @PostMapping("/MasterData/AddPlants")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "plants")
    public PlantsResponseDto addCarModule(@RequestBody PlantsRequestDto plantsRequestDtoRequestDto){
        return userfeignClientMasterData.addPlants(plantsRequestDtoRequestDto);
    }

    @GetMapping("/MasterData/GetAllPlants")
    public List<PlantsResponseDto> getAllPlants(){
        return userfeignClientMasterData.getAllPlants();
    }


    @PutMapping("/UpdatePlants/{id}")
    public ResponseEntity<PlantsResponseDto> updatePlant(
            @PathVariable Long id,
            @RequestBody PlantsRequestDto plantsRequestDto) {
        return userfeignClientMasterData.updatePlant(id,plantsRequestDto);
    }

    @DeleteMapping("DeletePlants/{id}")
    public ResponseEntity<PlantsResponseDto> softDeletePlant(@PathVariable Long id) {
        return userfeignClientMasterData.softDeletePlant(id);
    }

    @GetMapping("/MasterData/GetAllSuppliers")
    public List<Suppliers> getAllSuppliers(){
        return userfeignClientMasterData.getAllSuppliers();
    }

    @PostMapping("/MasterData/AddSuppliers")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "suppliers")
    public String addSuppliers(@RequestBody SupplierRequestDto suppilerRequestDto){
        userfeignClientMasterData.addSuppliers(suppilerRequestDto);
        return "SuccessFully SuppliersAdded";
    }

    @GetMapping("MasterData/GetsupplierById/{id}")
    public Suppliers getSupplierById(@PathVariable Long id){
        return userfeignClientMasterData.getSupplierById(id);
    }

    @GetMapping("/GetAllCarModule")
    public ResponseEntity<List<CarModuleResponseDto>> getAllCarModules() {
        return userfeignClientMasterData.getAllCarModules();
    }
}
