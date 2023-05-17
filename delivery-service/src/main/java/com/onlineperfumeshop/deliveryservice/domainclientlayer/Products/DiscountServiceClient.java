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
public class DiscountServiceClient {


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String DISCOUNT_SERVICE_BASE_URL;

    public DiscountServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.product-service.host}") String discountsServiceHost,
                               @Value("${app.product-service.port}") String discountsServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.DISCOUNT_SERVICE_BASE_URL = "http://" + discountsServiceHost + ":" + discountsServicePort + "/api/v1/discounts";
    }

    public DiscountResponseModel getDiscountsAggregate(String discountId) {
        DiscountResponseModel discountResponseModel;
        try {
            String url = DISCOUNT_SERVICE_BASE_URL + "/" + discountId;
            discountResponseModel = restTemplate
                    .getForObject(url, DiscountResponseModel.class);

            log.debug("5. Received in API-Gateway Discount Service Client getDiscountAggregate with discountResponseModel : " + discountResponseModel.getDiscountId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpDiscountException(ex);
        }
        return discountResponseModel;
    }

    private RuntimeException handleHttpDiscountException(HttpClientErrorException ex) {
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
    public DiscountResponseModel addDiscount(DiscountRequestModel discountRequestModel) {
        DiscountResponseModel discountResponseModel=new DiscountResponseModel();
        try {
            String url = DISCOUNT_SERVICE_BASE_URL;
            discountResponseModel = restTemplate.postForObject(url,discountRequestModel,DiscountResponseModel.class);

            log.debug("5. Received in addDiscount");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpDiscountException(ex);
        }
        return discountResponseModel;
    }



    public void deleteDiscount (String discountId) {
        try {
            String url = DISCOUNT_SERVICE_BASE_URL + "/" + discountId;
            restTemplate.delete(url);


            log.debug("5. in deleteDiscount");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpDiscountException(ex);
        }
    }

 */


    public DiscountResponseModel[] getDiscounts() {
        DiscountResponseModel[] discountResponseModels = null;
        try {
            String url = DISCOUNT_SERVICE_BASE_URL ;
            discountResponseModels = restTemplate
                    .getForObject(url, DiscountResponseModel[].class);


            log.debug("5. In Get all Discounts");
        } catch (HttpClientErrorException ex) {
            log.debug("5. getAll");
            throw handleHttpDiscountException(ex);
        }
        return discountResponseModels;
    }



}
