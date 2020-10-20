package com.example.vehiclefe.service;

import java.util.List;

import com.example.vehiclefe.model.Code;
import com.example.vehiclefe.model.Vehicle;


public interface VehicleService {

	List<Vehicle> vehicleList();

	List<Code> commonCategoryList();

	List<Code> commonModelList();

	String vehicleServerInfo();
    
}
