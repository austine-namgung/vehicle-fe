package com.example.vehiclefe.service;

import java.util.List;

import com.example.common.model.Code;
import com.example.vehiclefe.model.Vehicle;


public interface VehicleService {

	List<Vehicle> vehicleList();

	List<Code> commonCategoryList(String sleepYn);

	List<Code> commonModelList();

	List<Vehicle> mappedCodeNameVehicleList(List<Vehicle> vehicleList, List<Code> categoryList, List<Code> modelList);



    
}
