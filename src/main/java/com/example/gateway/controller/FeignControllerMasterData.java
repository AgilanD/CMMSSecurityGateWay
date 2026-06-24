package com.example.gateway.controller;


import cmms.HumanResource.entity.Plants;
import com.example.gateway.Dto.CarModuleRequestDto;
import com.example.gateway.Dto.CarModuleResponseDto;
import com.example.gateway.Dto.PlantsRequestDto;
import com.example.gateway.Dto.PlantsResponseDto;
import com.example.gateway.clients.UserFeignClientMasterData;
import com.example.gateway.common.AuditLogs;
import com.example.gateway.common.SupplierRequestDto;
import com.example.gateway.common.Suppliers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeignControllerMasterData {

    private final UserFeignClientMasterData userfeignClientMasterData;


    @GetMapping("/MasterData/Checking")
    public String checking(){
        return userfeignClientMasterData.Checkings();
    }

    @PostMapping("/MasterData/AddCustomer")
    public CarModuleResponseDto  addCarModule(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto){
        return userfeignClientMasterData. addCarModule(carModuleRequestDtoRequestDto);
    }

    @PostMapping("/MasterData/AddPlants")
    public PlantsResponseDto addCarModule(@RequestBody PlantsRequestDto PlantsRequestDtoRequestDto){
        return userfeignClientMasterData.addPlants(PlantsRequestDtoRequestDto);
    }

    @GetMapping("/MasterData/GetAllPlants")
    public List<Plants> GetAllPlants(){
        return userfeignClientMasterData.GetAllPlants();
    }


    @GetMapping("/MasterData/GetAllSuppliers")
    public List<Suppliers> GetAllSuppliers(){
        return userfeignClientMasterData.GetAllSuppliers();
    }

    @PostMapping("/MasterData/AddSuppliers")
    public String AddSuppliers(@RequestBody SupplierRequestDto suppilerRequestDto){
        userfeignClientMasterData.AddSuppliers(suppilerRequestDto);
        return "SuccessFully SuppliersAdded";
    }

    @GetMapping("MasterData/GetById/{id}")
    public Suppliers GetSupplierById(@RequestParam Long id){
        return userfeignClientMasterData.GetSupplierById(id);
    }



}
