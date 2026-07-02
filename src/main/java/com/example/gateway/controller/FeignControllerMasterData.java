package com.example.gateway.controller;

import com.example.gateway.Dto.CarModuleRequestDto;
import com.example.gateway.Dto.CarModuleResponseDto;
import com.example.gateway.Dto.PlantsRequestDto;
import com.example.gateway.Dto.PlantsResponseDto;
import com.example.gateway.clients.UserFeignClientMasterData;
import com.example.gateway.clients.UserFeignClientSystem;
import com.example.gateway.common.entity.SupplierRequestDto;
import com.example.gateway.common.entity.Suppliers;
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
    public List<?> GetAllPlants(){
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

    @GetMapping("MasterData/GetsupplierById/{id}")
    public Suppliers GetSupplierById(@PathVariable Long id){
        return userfeignClientMasterData.GetSupplierById(id);
    }

}
