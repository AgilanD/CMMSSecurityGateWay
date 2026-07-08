package com.example.gateway.clients;


import com.example.gateway.common.dto.CarModuleRequestDto;
import com.example.gateway.common.dto.CarModuleResponseDto;
import com.example.gateway.common.dto.PlantsRequestDto;
import com.example.gateway.common.dto.PlantsResponseDto;
import com.example.gateway.common.entity.SupplierRequestDto;
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
    public String addSuppliers(@RequestBody SupplierRequestDto suppilerRequestDto);

    @GetMapping("/GetsupplierById/{id}")
    public Suppliers getSupplierById(@PathVariable Long id);

    @PostMapping("/AddCarModule")
    public CarModuleResponseDto addCustomer(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto);

    @GetMapping("/GetAllCarModule")
    public ResponseEntity<List<CarModuleResponseDto>> getAllCarModules();



}
