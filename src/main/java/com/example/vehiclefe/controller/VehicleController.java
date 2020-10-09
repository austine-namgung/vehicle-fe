package com.example.vehiclefe.controller;

import java.util.List;

import com.example.vehiclefe.model.Vehicle;
import com.example.vehiclefe.service.VehicleService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RefreshScope
@RestController
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService service;

    @Value("${test.message}")
    private String testMessage;

    @GetMapping("/list")
    public List<Vehicle> list(){
        List<Vehicle> vehicleList =  service.vehicleList();
        return vehicleList;
    }

    @GetMapping("/configTest")
    public String configTest(){
        return testMessage;
    }

}
