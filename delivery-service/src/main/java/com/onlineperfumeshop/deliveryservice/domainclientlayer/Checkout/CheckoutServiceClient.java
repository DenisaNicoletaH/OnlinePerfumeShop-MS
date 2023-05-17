package com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout;

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
public class CheckoutServiceClient {


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CHECKOUTS_SERVICE_BASE_URL;

    public CheckoutServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.checkout-service.host}") String checkoutServiceHost,
                               @Value("${app.checkout-service.port}") String checkoutServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.CHECKOUTS_SERVICE_BASE_URL = "http://" + checkoutServiceHost + ":" + checkoutServicePort + "/api/v1/checkouts";
    }

    public CheckoutResponseModel getCheckoutAggregate(String checkoutId) {
        CheckoutResponseModel checkoutResponseModel;
        try {
            String url = CHECKOUTS_SERVICE_BASE_URL + "/" + checkoutId;
            checkoutResponseModel = restTemplate
                    .getForObject(url, CheckoutResponseModel.class);

            log.debug("5. Received in API-Gateway Checkout Service Client getCheckoutAggregate with checkoutResponseModel : " + checkoutResponseModel.getCheckoutId());
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpCheckoutException(ex);
        }
        return checkoutResponseModel;
    }

    private RuntimeException handleHttpCheckoutException(HttpClientErrorException ex) {
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
    public CheckoutResponseModel addCheckout(CheckoutRequestModel checkoutRequestModel) {
        CheckoutResponseModel checkoutResponseModel=new CheckoutResponseModel();
        try {
            String url = CHECKOUTS_SERVICE_BASE_URL;
            checkoutResponseModel = restTemplate.postForObject(url,checkoutRequestModel,CheckoutResponseModel.class);

            log.debug("5. Received in addCheckout");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpCheckoutException(ex);
        }
        return checkoutResponseModel;
    }




    public void updateCheckout(CheckoutRequestModel checkoutRequestModel, String checkoutId) {
        try {
            String url = CHECKOUTS_SERVICE_BASE_URL + "/" + checkoutId;
            restTemplate.put(url, checkoutRequestModel);

            log.debug("5. in UpdateCheckout");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpCheckoutException(ex);
        }
    }


    public void deleteCheckout (String checkoutId) {
        try {
            String url = CHECKOUTS_SERVICE_BASE_URL + "/" + checkoutId;
            restTemplate.delete(url);


            log.debug("5. in DeleteCheckout");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpCheckoutException(ex);
        }
    }


 */

    public CheckoutResponseModel[] getCheckouts() {
        CheckoutResponseModel[] checkoutResponseModels = null;
        try {
            String url = CHECKOUTS_SERVICE_BASE_URL ;
            checkoutResponseModels = restTemplate
                    .getForObject(url, CheckoutResponseModel[].class);


            log.debug("5. In Get all Checkout");
        } catch (HttpClientErrorException ex) {
            log.debug("5. get");
            throw handleHttpCheckoutException(ex);
        }
        return checkoutResponseModels;
    }





}
