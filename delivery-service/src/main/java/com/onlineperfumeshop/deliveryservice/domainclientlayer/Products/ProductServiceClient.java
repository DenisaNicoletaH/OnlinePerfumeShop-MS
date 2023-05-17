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
public class ProductServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String PRODUCT_SERVICE_BASE_URL;

    public ProductServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.product-service.host}") String productsServiceHost,
                               @Value("${app.product-service.port}") String productsServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.PRODUCT_SERVICE_BASE_URL = "http://" + productsServiceHost + ":" + productsServicePort + "/api/v1/products";
    }

    public ProductResponseModel getProductAggregate(String productId) {
        ProductResponseModel productResponseModel;
        try {
            String url = PRODUCT_SERVICE_BASE_URL + "/" + productId;
            productResponseModel = restTemplate
                    .getForObject(url, ProductResponseModel.class);


        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpProductException(ex);
        }
        return productResponseModel;
    }

    private RuntimeException handleHttpProductException(HttpClientErrorException ex) {
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
    public ProductResponseModel addProduct(ProductRequestModel productRequestModel) {
        ProductResponseModel productResponseModel=new ProductResponseModel();
        try {
            String url = PRODUCT_SERVICE_BASE_URL;
            productResponseModel = restTemplate.postForObject(url,productRequestModel,ProductResponseModel.class);

            log.debug("5. Received in AddProduct");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpProductException(ex);
        }
        return productResponseModel;
    }
*/




/*
    public void updateProduct(ProductRequestModel productRequestModel, String productId) {
        try {
            String url = PRODUCT_SERVICE_BASE_URL + "/" + productId;
            restTemplate.put(url, productRequestModel);

            log.debug("5. in updateProduct");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpProductException(ex);
        }
    }

 */

    public void deleteProduct (String productId) {
        try {
            String url = PRODUCT_SERVICE_BASE_URL + "/" + productId;
            restTemplate.delete(url);


            log.debug("5. in deleteProduct");
        } catch (HttpClientErrorException ex) {
            log.debug("5. delete");
            throw handleHttpProductException(ex);
        }
    }


    public ProductResponseModel[] getProducts() {
        ProductResponseModel[] productResponseModels = null;
        try {
            String url = PRODUCT_SERVICE_BASE_URL ;
            productResponseModels = restTemplate
                    .getForObject(url, ProductResponseModel[].class);


            log.debug("5. In Get all Products");
        } catch (HttpClientErrorException ex) {
            log.debug("5. getAll");
            throw handleHttpProductException(ex);
        }
        return productResponseModels;
    }




    public ProductResponseModel[] getProductByDiscountId(String discountId) {
        ProductResponseModel[] productResponseModel = null;
        try {
            String url = PRODUCT_SERVICE_BASE_URL + "/" + discountId ;
            productResponseModel = restTemplate
                    .getForObject(url,ProductResponseModel[].class);


            log.debug("5. In Get all Clients");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpProductException(ex);
        }
        return productResponseModel;
    }


}

