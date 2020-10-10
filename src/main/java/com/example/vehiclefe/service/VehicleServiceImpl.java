package com.example.vehiclefe.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.common.model.Code;
import com.example.vehiclefe.model.Vehicle;
import com.example.vehiclefe.utils.RedisManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {


    private  WebClient vehicleWebClient;
    private  WebClient commonWebClient;
    @Autowired
    private  RedisManager<Code> redisManager;

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

        List<Vehicle> vehicleList =  vehicleWebClient
            .get().uri("/api/vehicles")
            .retrieve()
            .bodyToFlux(Vehicle.class)
            .collectList()
            .block();
       
        return vehicleList;
        
    }

    @HystrixCommand(
		fallbackMethod = "fallbackCommonCategoryList",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "100")
		})
    @Override
    public List<Code> commonCategoryList() {
        return  commonWebClient
            .get()
            .uri("/api/common/categories")
            .retrieve()
            .bodyToFlux(Code.class)
            .collectList()
            .block();
        
    }

    @HystrixCommand(
		fallbackMethod = "fallbackCommonModelList",
		commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "2000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "100")
		})
    @Override
    public List<Code> commonModelList() {
        return  commonWebClient
            .get()
            .uri("/api/common/models")
            .retrieve()
            .bodyToFlux(Code.class)
            .collectList()
            .block();
    }

    @Override
    public List<Vehicle>  mappedCodeNameVehicleList(List<Vehicle> vehicleList,List<Code> categoryList,List<Code> modelList ){

        return vehicleList.stream().map(v -> {
            Code category =  categoryList
                .stream()
                .filter(c -> c.getCodeId().equals(v.getCategory()))
                .findFirst()
                .orElse(null);
            v.setCategoryName(category.getCodeName()); 
            
            Code model =  modelList
                .stream()
                .filter(m -> m.getCodeId().equals(v.getModel()))
                .findFirst()
                .orElse(null);
            v.setModelName(model.getCodeName()); 

            return v;              
         }).collect(Collectors.toList());

    }

    

    public List<Code> fallbackCommonCategoryList() {
        log.info("=====error==fallbackCommonCategoryList");
       
        List<Code> categoryList  = redisManager.getListValue("common::category-all");
        return categoryList;
    }
    
    public List<Code> fallbackCommonModelList() {
		log.info("=====error==fallbackCommonModelList");
		
        List<Code> modelList  = redisManager.getListValue("common::model-all");
        return modelList;
    }
    
    
    
}
