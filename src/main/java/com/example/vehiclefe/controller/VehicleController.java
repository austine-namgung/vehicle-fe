package com.example.vehiclefe.controller;

import java.util.List;

import com.example.common.model.Code;
import com.example.vehiclefe.model.Vehicle;
import com.example.vehiclefe.service.VehicleService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

  

    @GetMapping("/list")    
    public String  list(Model model){
        List<Vehicle> vehicleList =  service.vehicleList();
        List<Code> categoryList = service.commonCategoryList();
        List<Code> modelList = service.commonModelList();
        List<Vehicle> vehicMappedleList =  service.mappedCodeNameVehicleList(vehicleList, categoryList,modelList);
        model.addAttribute("vehicleList", vehicMappedleList);
        return "vehicle/list.html";
         
    }

    @GetMapping("/json/list")  
    @ResponseBody  
    public List<Vehicle> jsonList(){
        List<Vehicle> vehicleList =  service.vehicleList();
        List<Code> categoryList = service.commonCategoryList();
        List<Code> modelList = service.commonModelList();
        return service.mappedCodeNameVehicleList(vehicleList, categoryList,modelList);
         
    }


    

}
