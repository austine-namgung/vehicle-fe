package com.example.vehiclefe.controller;

import java.util.List;

import com.example.vehiclefe.model.Vehicle;
import com.example.vehiclefe.service.VehicleService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RefreshScope
@Controller
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

    @Value("${test.message}")
    private String testMessage;

    @GetMapping("/list")
    public String list(Model model){
        List<Vehicle> vehicleList =  service.vehicleList();
        model.addAttribute("vehicleList", vehicleList);
        return "vehicle/list.html";
        
    }
    @GetMapping("/json/list")  
    @ResponseBody  
    public List<Vehicle> jsonList(){
        List<Vehicle> vehicleList =  service.vehicleList();
        return vehicleList;
         
    }

    @GetMapping("/configTest")
    @ResponseBody
    public String configTest(){
        return testMessage;
    }

}
