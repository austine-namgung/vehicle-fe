package com.example.vehiclefe.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.example.vehiclefe.model.ApiCallDto;
import com.example.vehiclefe.model.Code;
import com.example.vehiclefe.model.Vehicle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static String SUCCESS = "Y";

    private final WebClient vehicleWebClient;
    private final WebClient commonWebClient;
    private final WebClient gatewayWebClient;

    public VehicleServiceImpl(@Value("${api.vehicle.url}") final String vehicleApiUrl,
                              @Value("${api.common.url}") final String commonApiUrl,
                              @Value("${api.gateway.url}") final String gatewayUrl) {
        vehicleWebClient = WebClient.builder().baseUrl(vehicleApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        commonWebClient = WebClient.builder().baseUrl(commonApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        gatewayWebClient = WebClient.builder().baseUrl(gatewayUrl)
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

    @Override
    public Object callApiGateway(ApiCallDto apiCallDto) {
        switch (apiCallDto.getApiType()) {
            case "vehicles":
                switch (apiCallDto.getAuthMethod()) {
                    case "Basic": {
                        String text = apiCallDto.getUsername() + ":" + apiCallDto.getPassword();
                        String encoded = new String(Base64.getEncoder().encode(text.getBytes()));
                        List<Vehicle> vehicles = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Basic " + encoded)
                                .build()
                                .get()
                                .uri("/basic-auth/vehicles")
                                .retrieve()
                                .bodyToFlux(Vehicle.class)
                                .collectList()
                                .block();

                        return vehicles;
                    }
                    case "ApiKey": {

                        List<Vehicle> vehicles = gatewayWebClient.mutate()
                                .defaultHeader("apiKey", apiCallDto.getApiKey())
                                .build()
                                .get()
                                .uri("/api-key/vehicles")
                                .retrieve()
                                .bodyToFlux(Vehicle.class)
                                .collectList()
                                .block();

                        return vehicles;
                    }
                    case "JWT": {
                        List<Vehicle> vehicles = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/jwt/vehicles")
                                .retrieve()
                                .bodyToFlux(Vehicle.class)
                                .collectList()
                                .block();

                        return vehicles;
                    }
                    case "Oauth2": {
                        List<Vehicle> vehicles = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/oauth2/vehicles")
                                .retrieve()
                                .bodyToFlux(Vehicle.class)
                                .collectList()
                                .block();

                        return vehicles;
                    }
                }
                break;
            case "categories":
                switch (apiCallDto.getAuthMethod()) {
                    case "Basic": {
                        String text = apiCallDto.getUsername() + ":" + apiCallDto.getPassword();
                        String encoded = new String(Base64.getEncoder().encode(text.getBytes()));
                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Basic " + encoded)
                                .build()
                                .get()
                                .uri("/basic-auth/common/categories")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                    case "ApiKey": {

                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("apiKey", apiCallDto.getApiKey())
                                .build()
                                .get()
                                .uri("/api-key/common/categories")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                    case "JWT": {
                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/jwt/common/categories")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                    case "Oauth2": {
                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/oauth2/common/categories")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                }
                break;
            case "models":
                switch (apiCallDto.getAuthMethod()) {
                    case "Basic": {
                        String text = apiCallDto.getUsername() + ":" + apiCallDto.getPassword();
                        String encoded = new String(Base64.getEncoder().encode(text.getBytes()));
                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Basic " + encoded)
                                .build()
                                .get()
                                .uri("/basic-auth/common/models")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                    case "ApiKey": {

                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("apiKey", apiCallDto.getApiKey())
                                .build()
                                .get()
                                .uri("/api-key/common/models")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                    case "JWT": {
                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/jwt/common/models")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                    case "Oauth2": {
                        List<Code> categories = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/oauth2/common/models")
                                .retrieve()
                                .bodyToFlux(Code.class)
                                .collectList()
                                .block();

                        return categories;
                    }
                }
                break;
            case "category":
                switch (apiCallDto.getAuthMethod()) {
                    case "Basic": {
                        String text = apiCallDto.getUsername() + ":" + apiCallDto.getPassword();
                        String encoded = new String(Base64.getEncoder().encode(text.getBytes()));
                        Mono<Code> category = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Basic " + encoded)
                                .build()
                                .get()
                                .uri("/basic-auth/common/categories/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);

                        return category;
                    }
                    case "ApiKey": {

                        Mono<Code> category = gatewayWebClient.mutate()
                                .defaultHeader("apiKey", apiCallDto.getApiKey())
                                .build()
                                .get()
                                .uri("/api-key/common/categories/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);

                        return category;
                    }
                    case "JWT": {
                        Mono<Code> category = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/jwt/common/categories/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);

                        return category;
                    }
                    case "Oauth2": {
                        Mono<Code> category = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/oauth2/common/categories/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);

                        return category;
                    }
                }
                break;
            case "model":
                switch (apiCallDto.getAuthMethod()) {
                    case "Basic": {
                        String text = apiCallDto.getUsername() + ":" + apiCallDto.getPassword();
                        String encoded = new String(Base64.getEncoder().encode(text.getBytes()));
                        Mono<Code> model = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Basic " + encoded)
                                .build()
                                .get()
                                .uri("/basic-auth/common/models/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);
                        return model;
                    }
                    case "ApiKey": {

                        Mono<Code> model = gatewayWebClient.mutate()
                                .defaultHeader("apiKey", apiCallDto.getApiKey())
                                .build()
                                .get()
                                .uri("/api-key/common/models/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);

                        return model;
                    }
                    case "JWT": {
                        Mono<Code> model = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/jwt/common/models/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);


                        return model;
                    }
                    case "Oauth2": {
                        Mono<Code> model = gatewayWebClient.mutate()
                                .defaultHeader("Authorization", "Bearer " + apiCallDto.getAccessToken())
                                .build()
                                .get()
                                .uri("/oauth2/common/models/"+apiCallDto.getCodeId())
                                .retrieve()
                                .bodyToMono(Code.class);

                        return model;
                    }
                }
                break;
        }
        return null;
    }

    @Override
    public List<Vehicle> vehicleListFromApiGateway(String auth) {
        WebClient.create()
                .get()
                .header("test", "value")
                .headers(headers -> {
                    headers.add("test_1", "value_1");
                    headers.add("test_2", "value_2");
                });
        return null;
    }

    @Override
    public List<Code> commonCategoryListFromApiGateway(String auth) {
        return null;
    }

    @Override
    public List<Code> commonModelListFromApiGateway(String auth) {
        return null;
    }

    @Override
    public Code commonCategoryFromApiGateway(String codeId, String auth) {
        return null;
    }

    @Override
    public Code commonModelFromApiGateway(String codeId, String auth) {
        return null;
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
