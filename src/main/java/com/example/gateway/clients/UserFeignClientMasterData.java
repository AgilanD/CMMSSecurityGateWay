package com.example.gateway.clients;


import cmms.HumanResource.entity.Plants;
import com.example.gateway.Dto.CarModuleRequestDto;
import com.example.gateway.Dto.CarModuleResponseDto;
import com.example.gateway.Dto.PlantsRequestDto;
import com.example.gateway.Dto.PlantsResponseDto;
import com.example.gateway.common.entity.SupplierRequestDto;
import com.example.gateway.common.entity.Suppliers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MasterData", url = "http://localhost:8083/MasterData")
public interface UserFeignClientMasterData {


    @GetMapping("/Checking")
    public String Checkings();

    @PostMapping("/AddCustomer")
    public CarModuleResponseDto addCarModule(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto);


    @PostMapping("/AddPlants")
    public PlantsResponseDto addPlants(@RequestBody PlantsRequestDto plantsRequestDto);

    @GetMapping("/GetAllPlants")
    public List<Plants> GetAllPlants();

    @GetMapping("/GetAllSuppliers")
    public List<Suppliers> GetAllSuppliers();

    @PostMapping("/AddSuppliers")
    public String AddSuppliers(@RequestBody SupplierRequestDto suppilerRequestDto);

    @GetMapping("/GetById/{id}")
    public Suppliers GetSupplierById(@RequestParam Long id);




}
