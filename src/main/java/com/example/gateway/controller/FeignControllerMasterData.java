package com.example.gateway.controller;

import com.example.gateway.common.dto.CarModuleRequestDto;
import com.example.gateway.common.dto.CarModuleResponseDto;
import com.example.gateway.common.dto.PlantsRequestDto;
import com.example.gateway.common.dto.PlantsResponseDto;
import com.example.gateway.clients.UserFeignClientMasterData;
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
        return userfeignClientMasterData.checkingMessage();
    }

    @PostMapping("/MasterData/AddCustomer")
    public CarModuleResponseDto  addCarModule(@RequestBody CarModuleRequestDto carModuleRequestDtoRequestDto){


        return userfeignClientMasterData. addCarModule(carModuleRequestDtoRequestDto);
    }

    @PostMapping("/MasterData/AddPlants")
    public PlantsResponseDto addCarModule(@RequestBody PlantsRequestDto plantsRequestDtoRequestDto){
        return userfeignClientMasterData.addPlants(plantsRequestDtoRequestDto);
    }

    @GetMapping("/MasterData/GetAllPlants")
    public List<PlantsResponseDto> getAllPlants(){
        return userfeignClientMasterData.getAllPlants();
    }


    @GetMapping("/MasterData/GetAllSuppliers")
    public List<Suppliers> getAllSuppliers(){
        return userfeignClientMasterData.getAllSuppliers();
    }

    @PostMapping("/MasterData/AddSuppliers")
    public String addSuppliers(@RequestBody SupplierRequestDto suppilerRequestDto){
        userfeignClientMasterData.addSuppliers(suppilerRequestDto);
        return "SuccessFully SuppliersAdded";
    }

    @GetMapping("MasterData/GetsupplierById/{id}")
    public Suppliers getSupplierById(@PathVariable Long id){
        return userfeignClientMasterData.getSupplierById(id);
    }

}
