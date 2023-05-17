package com.onlineperfumeshop.deliveryservice.domainclientlayer.Products;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryInvalidInputException;
import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryNotFoundException;
import com.onlineperfumeshop.deliveryservice.Utils.HttpErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Component
public class InventoryServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String INVENTORIES_SERVICE_BASE_URL;

    public InventoryServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.product-service.host}") String inventoriesServiceHost,
                               @Value("${app.product-service.port}") String inventoriesServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.INVENTORIES_SERVICE_BASE_URL = "http://" + inventoriesServiceHost + ":" + inventoriesServicePort + "/api/v1/inventories";
    }

    public InventoryResponseModel getInventoryAggregate(String inventoryId) {
        InventoryResponseModel inventoryResponseModel;
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;
            inventoryResponseModel = restTemplate
                    .getForObject(url, InventoryResponseModel.class);

            log.debug("5. Received in API-Gateway Inventory Service Client getInventoryAggregate with inventoryResponseModel : " + inventoryResponseModel.getInventoryId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpInventoriesException(ex);
        }
        return inventoryResponseModel;
    }

    private RuntimeException handleHttpInventoriesException(HttpClientErrorException ex) {

        if (ex.getStatusCode() == NOT_FOUND) {
            return new DeliveryNotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new DeliveryInvalidInputException(getErrorMessage(ex));
        }
        log.warn("Got a unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());
        return ex;
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return objectMapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ioex.getMessage();
        }
    }



/*
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel) {
        InventoryResponseModel inventoryResponseModel=new InventoryResponseModel();
        try {
            String url = INVENTORIES_SERVICE_BASE_URL ;
            inventoryResponseModel = restTemplate.postForObject(url,inventoryRequestModel,InventoryResponseModel.class);

            log.debug("5. Received in AddInventory");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpInventoriesException(ex);
        }
        return inventoryResponseModel;
    }




    public void updateInventory(InventoryRequestModel inventoryRequestModel, String inventoryId) {
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;
            restTemplate.put(url, inventoryRequestModel);

            log.debug("5. in UpdateInventory");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpInventoriesException(ex);
        }
    }


    public void deleteInventory (String inventoryId) {
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId;
            restTemplate.delete(url);


            log.debug("5. in deleteInventory");
        } catch (HttpClientErrorException ex) {
            log.debug("5. delete");
            throw handleHttpInventoriesException(ex);
        }
    }

      public ProductResponseModel addProductToInventory(ProductRequestModel productRequestModel, String inventoryId) {
        ProductResponseModel productResponseModel=new ProductResponseModel();
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId + "/products";
             productResponseModel = restTemplate.postForObject(url, productRequestModel, ProductResponseModel.class);

            log.debug("5. Received in AddProduct");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpInventoriesException(ex);

        }
        return productResponseModel;
    }

 */


    public InventoryResponseModel[] getInventories() {
        InventoryResponseModel[] inventoryResponseModels = null;
        try {
            String url = INVENTORIES_SERVICE_BASE_URL;
            inventoryResponseModels = restTemplate
                    .getForObject(url, InventoryResponseModel[].class);


            log.debug("5. In Get all Clients");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpInventoriesException(ex);
        }
        return inventoryResponseModels;
    }


    public ProductResponseModel[] getProductsByInventoryIdentifier_InventoryId(String inventoryId) {
        ProductResponseModel[] productResponseModel = null;
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + inventoryId + "/products" ;
            productResponseModel = restTemplate
                    .getForObject(url,ProductResponseModel[].class);


            log.debug("5. In Get all Clients");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpInventoriesException(ex);
        }
        return productResponseModel;
    }





    public ProductResponseModel[] getProductByBrand(String brand) {
        ProductResponseModel[] productResponseModel = null;
        try {
            String url = INVENTORIES_SERVICE_BASE_URL + "/" + brand ;
            productResponseModel = restTemplate
                    .getForObject(url,ProductResponseModel[].class);


            log.debug("5. In Get all Clients");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpInventoriesException(ex);
        }
        return productResponseModel;
    }



}
