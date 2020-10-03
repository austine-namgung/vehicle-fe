package com.example.vehiclefe.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.vehiclefe.model.Code;
import com.example.vehiclefe.model.Vehicle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static String SUCCESS = "Y";

    private final WebClient vehicleWebClient;
    private final WebClient commonWebClient;

    public VehicleServiceImpl(@Value("${api.vehicle.url}") final String vehicleApiUrl, @Value("${api.common.url}") final String commonApiUrl) {
        vehicleWebClient = WebClient.builder().baseUrl(vehicleApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        commonWebClient = WebClient.builder().baseUrl(commonApiUrl)               
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)               
                .build();
    }

    @Override
    public List<Vehicle> vehicleList() {

        List<Vehicle> vehicleList =  vehicleWebClient.get().uri("/api/vehicles").retrieve().bodyToFlux(Vehicle.class).collectList().block();
        List<Code> categoryList = commonCategoryList();
        List<Code> modelList = commonModelList();
        return mappedCodeNameVehicleList(vehicleList, categoryList,modelList);
        
    }

    @Override
    public List<Code> commonCategoryList() {
        return  commonWebClient.get().uri("/api/common/categories").retrieve().bodyToFlux(Code.class).collectList().block();
        
    }

    @Override
    public List<Code> commonModelList() {
        return  commonWebClient.get().uri("/api/common/models").retrieve().bodyToFlux(Code.class).collectList().block();
    }

    private List<Vehicle>  mappedCodeNameVehicleList(List<Vehicle> vehicleList,List<Code> categoryList,List<Code> modelList ){

        return vehicleList.stream().map(v -> {
            Code category =  categoryList.stream().filter(c -> c.getCodeId().equals(v.getCategory())).findFirst().orElse(null);
            v.setCategoryName(category.getCodeName()); 
            
            Code model =  modelList.stream().filter(m -> m.getCodeId().equals(v.getModel())).findFirst().orElse(null);
            v.setModelName(model.getCodeName()); 

            return v;              
         }).collect(Collectors.toList());

    }
    
}
