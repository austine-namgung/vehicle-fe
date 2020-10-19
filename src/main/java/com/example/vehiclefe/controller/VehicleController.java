package com.example.vehiclefe.controller;

import java.util.List;

import com.example.vehiclefe.model.ApiCallDto;
import com.example.vehiclefe.model.Code;
import com.example.vehiclefe.model.Vehicle;
import com.example.vehiclefe.service.VehicleService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VehicleController {
    private final VehicleService service;

    @GetMapping("/list")
    public List<Vehicle> list(){
        List<Vehicle> vehicleList =  service.vehicleList();
        return vehicleList;
    }

    @PostMapping("/result")
    public Object result(@RequestBody ApiCallDto apiCallDto){
        return service.callApiGateway(apiCallDto);
    }



}
