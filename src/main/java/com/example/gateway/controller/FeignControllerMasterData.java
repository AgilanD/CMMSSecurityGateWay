package com.example.gateway.controller;


import com.example.gateway.aspect.AuditLoggable;
import com.example.gateway.common.dto.*;
import com.example.gateway.clients.UserFeignClientMasterData;
import com.example.gateway.common.entity.Suppliers;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


    @PutMapping("/MasterData/UpdatePlants/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "plants")
    public ResponseEntity<PlantsResponseDto> updatePlant(
            @PathVariable Long id,
            @RequestBody PlantsRequestDto plantsRequestDto) {
        return userfeignClientMasterData.updatePlant(id,plantsRequestDto);
    }

    @DeleteMapping("/MasterData/DeletePlants/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "plants")
    public ResponseEntity<PlantsResponseDto> softDeletePlant(@PathVariable Long id) {
        return userfeignClientMasterData.softDeletePlant(id);
    }

    @GetMapping("/MasterData/GetAllSuppliers")
    public List<Suppliers> getAllSuppliers(){
        return userfeignClientMasterData.getAllSuppliers();
    }

    @PostMapping("/MasterData/AddSuppliers")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.CREATE, tableName = "suppliers")
    public ResponseEntity<cmms.masterdata.dto.SupplierResponseDto> addSupplier(@RequestBody cmms.masterdata.dto.SupplierRequestDto supplierRequestDto) {

        ResponseEntity<cmms.masterdata.dto.SupplierResponseDto> feignResponse = userfeignClientMasterData.addSupplier(supplierRequestDto);
        cmms.masterdata.dto.SupplierResponseDto responseDto = feignResponse.getBody();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/MasterData/GetsupplierById/{id}")
    public Suppliers getSupplierById(@PathVariable Long id){
        return userfeignClientMasterData.getSupplierById(id);
    }

    @GetMapping("/MasterData/GetAllCarModules")
    public ResponseEntity<List<CarModuleResponseDto>> getAllCarModules() {
        return userfeignClientMasterData.getAllCarModules();
    }

    @GetMapping("/MasterData/GetCarModuleById/{id}")
    public ResponseEntity<CarModuleResponseDto> getCarModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(userfeignClientMasterData.getCarModuleById(id).getBody());
    }

    @PutMapping("/MasterData/UpdateCarModule/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "car_module")
    public ResponseEntity<CarModuleResponseDto> updateCarModule(
            @PathVariable Long id,
            @RequestBody CarModuleRequestDto carModuleRequestDto) {
        return ResponseEntity.ok(userfeignClientMasterData.updateCarModule(id, carModuleRequestDto).getBody());
    }

    @DeleteMapping("/MasterData/DeleteCarModule/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "car_module")
    public ResponseEntity<String> deleteCarModule(@PathVariable Long id) {
        userfeignClientMasterData.deleteCarModule(id);
        return ResponseEntity.ok("Car module deleted successfully with ID: " + id);
    }
    @GetMapping("/MasterData/GetPlantById/{id}")
    public ResponseEntity<PlantsResponseDto> getPlantById(@PathVariable Long id) {
        return ResponseEntity.ok(userfeignClientMasterData.getPlantById(id).getBody());
    }

    @PutMapping("/UpdateSuppliers/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.UPDATE, tableName = "suppliers")
    public ResponseEntity<SupplierResponseDto> updateSupplier(
            @PathVariable Long id,
            @Valid @RequestBody cmms.masterdata.dto.SupplierRequestDto supplierRequestDto) {
        return ResponseEntity.ok(userfeignClientMasterData.updateSupplier(id, supplierRequestDto).getBody());
    }

    @DeleteMapping("/DeleteSuppliers/{id}")
    @AuditLoggable(action = AuditLogsRequestDto.AuditAction.DELETE, tableName = "suppliers")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        userfeignClientMasterData.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully with ID: " + id);
    }
}
