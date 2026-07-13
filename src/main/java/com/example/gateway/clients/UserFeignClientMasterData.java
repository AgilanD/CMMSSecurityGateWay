package com.example.gateway.clients;


import cmms.masterdata.dto.SupplierRequestDto;
import com.example.gateway.common.dto.*;
import com.example.gateway.common.entity.Suppliers;
import com.example.gateway.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "MasterData", url = "http://localhost:8083/MasterData",configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClientMasterData {


    @PostMapping("/AddCarModule")
    public CarModuleResponseDto addCarModule(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto);

    @PostMapping("/AddPlants")
    public PlantsResponseDto addPlants(@RequestBody PlantsRequestDto plantsRequestDto);

    @GetMapping("/GetAllPlants")
    public List<PlantsResponseDto> getAllPlants();

    @GetMapping("/GetAllSuppliers")
    public List<Suppliers> getAllSuppliers();

    @PostMapping("/AddSuppliers")
    public ResponseEntity<cmms.masterdata.dto.SupplierResponseDto> addSupplier(@RequestBody SupplierRequestDto supplierRequestDto);

    @GetMapping("/GetsupplierById/{id}")
    public Suppliers getSupplierById(@PathVariable Long id);

    @PostMapping("/AddCarModule")
    public CarModuleResponseDto addCustomer(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto);

    @GetMapping("/GetAllCarModules")
    ResponseEntity<List<CarModuleResponseDto>> getAllCarModules();

    @PutMapping("/UpdatePlants/{id}")
    public ResponseEntity<PlantsResponseDto> updatePlant(@PathVariable Long id, @RequestBody PlantsRequestDto plantsRequestDto);

    @DeleteMapping("/DeletePlants/{id}")
    public ResponseEntity<PlantsResponseDto> softDeletePlant(@PathVariable Long id);

    @GetMapping("/GetCarModuleById/{id}")
    public ResponseEntity<CarModuleResponseDto> getCarModuleById(@PathVariable Long id);

    @PutMapping("/UpdateCarModule/{id}")
    public ResponseEntity<CarModuleResponseDto> updateCarModule(
            @PathVariable Long id,
            @RequestBody CarModuleRequestDto carModuleRequestDto);

    @DeleteMapping("/DeleteCarModule/{id}")
    public ResponseEntity<String> deleteCarModule(@PathVariable Long id);

    @GetMapping("/GetPlantById/{id}")
    public ResponseEntity<PlantsResponseDto> getPlantById(@PathVariable Long id);

    @PutMapping("/UpdateSuppliers/{id}")
    public ResponseEntity<SupplierResponseDto> updateSupplier(
            @PathVariable Long id,
            @RequestBody cmms.masterdata.dto.SupplierRequestDto supplierRequestDto);

    @DeleteMapping("/DeleteSuppliers/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id);

}
