package com.example.vehiclefe.controller;

import java.util.List;

import com.example.common.model.Code;
import com.example.vehiclefe.model.Vehicle;
import com.example.vehiclefe.service.VehicleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

    @GetMapping("/list")
    public List<Vehicle> list(){
        List<Vehicle> vehicleList =  service.vehicleList();
        List<Code> categoryList = service.commonCategoryList();
        List<Code> modelList = service.commonModelList();
        return service.mappedCodeNameVehicleList(vehicleList, categoryList,modelList);
         
    }


    

}
