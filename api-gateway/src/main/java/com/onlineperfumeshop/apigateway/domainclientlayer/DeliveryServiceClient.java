package com.onlineperfumeshop.apigateway.domainclientlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.ClientInvalidInputException;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.NotFoundException;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Delivery.DeliveryInvalidInputException;
import com.onlineperfumeshop.apigateway.Utils.HttpErrorInfo;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientResponseModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Delivery.DeliveryRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Delivery.DeliveryResponseModel;
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
public class DeliveryServiceClient {


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String DELIVERY_SERVICE_BASE_URL;

    public DeliveryServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.delivery-service.host}") String deliveryServiceHost,
                               @Value("${app.delivery-service.port}") String deliveryServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.DELIVERY_SERVICE_BASE_URL = "http://" + deliveryServiceHost + ":" + deliveryServicePort + "/api/v1/deliveries";
    }

    public DeliveryResponseModel getDeliveryAggregate(String deliveryId) {
        DeliveryResponseModel deliveryResponseModel= new DeliveryResponseModel();
        try {
            String url = DELIVERY_SERVICE_BASE_URL + "/" + deliveryId;
            deliveryResponseModel = restTemplate
                    .getForObject(url,DeliveryResponseModel.class);

        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpDeliveryException(ex);
        }
        return deliveryResponseModel;
    }

    private RuntimeException handleHttpDeliveryException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
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


    public DeliveryResponseModel addDelivery(DeliveryRequestModel deliveryRequestModel) {
        DeliveryResponseModel deliveryResponseModel=new DeliveryResponseModel();
        try {
            String url = DELIVERY_SERVICE_BASE_URL;
            deliveryResponseModel = restTemplate.postForObject(url,deliveryRequestModel,DeliveryResponseModel.class);

            log.debug("5. Received in addDeliveries");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpDeliveryException(ex);
        }
        return deliveryResponseModel;
    }




    public void updateDeliveries(DeliveryRequestModel deliveryRequestModel, String deliveryId) {
        try {
            String url = DELIVERY_SERVICE_BASE_URL + "/" + deliveryId ;
            restTemplate.put(url, deliveryRequestModel);

            log.debug("5. in UpdateDelivery");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpDeliveryException(ex);
        }
    }


    public void deleteDelivery (String deliveryId) {
        try {
            String url = DELIVERY_SERVICE_BASE_URL + "/" + deliveryId;
            restTemplate.delete(url);


            log.debug("5. in DeleteDelivery");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpDeliveryException(ex);
        }
    }

//get All
    public DeliveryResponseModel[] getDeliveries() {
        DeliveryResponseModel deliveryResponseModel[] = null;
        try {
            String url = DELIVERY_SERVICE_BASE_URL ;
            deliveryResponseModel= restTemplate
                    .getForObject(url, DeliveryResponseModel[].class);


            log.debug("5. In Get all Clients");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpDeliveryException(ex);
        }
        return deliveryResponseModel;
    }

}
