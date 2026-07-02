package com.example.gateway.clients;


import com.example.gateway.Dto.CarModuleRequestDto;
import com.example.gateway.Dto.CarModuleResponseDto;
import com.example.gateway.Dto.PlantsRequestDto;
import com.example.gateway.Dto.PlantsResponseDto;
import com.example.gateway.common.entity.SupplierRequestDto;
import com.example.gateway.common.entity.Suppliers;
import com.example.gateway.config.FeignClientInterceptorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "MasterData", url = "http://localhost:8083/MasterData",configuration = FeignClientInterceptorConfig.class)
public interface UserFeignClientMasterData {


    @GetMapping("/Checking")
    public String Checkings();

    @PostMapping("/AddCustomer")
    public CarModuleResponseDto addCarModule(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto);


    @PostMapping("/AddPlants")
    public PlantsResponseDto addPlants(@RequestBody PlantsRequestDto plantsRequestDto);

    @GetMapping("/GetAllPlants")
    public List<?> GetAllPlants();

    @GetMapping("/GetAllSuppliers")
    public List<Suppliers> GetAllSuppliers();

    @PostMapping("/AddSuppliers")
    public String AddSuppliers(@RequestBody SupplierRequestDto suppilerRequestDto);

    @GetMapping("/GetsupplierById/{id}")
    public Suppliers GetSupplierById(@PathVariable Long id);




}
