package com.example.vehiclefe.service;

import java.util.List;

import com.example.vehiclefe.model.ApiCallDto;
import com.example.vehiclefe.model.Code;
import com.example.vehiclefe.model.Vehicle;


public interface VehicleService {

	List<Vehicle> vehicleList();

	List<Code> commonCategoryList();

	List<Code> commonModelList();

	Object callApiGateway(ApiCallDto apiCallDto);
	List<Vehicle> vehicleListFromApiGateway(String auth);

	List<Code> commonCategoryListFromApiGateway(String auth);

	List<Code> commonModelListFromApiGateway(String auth);

	Code commonCategoryFromApiGateway(String codeId, String auth);

	Code commonModelFromApiGateway(String codeId, String auth);

}
