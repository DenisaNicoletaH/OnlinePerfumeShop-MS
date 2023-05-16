package com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.onlineperfumeshop.deliveryservice.Utils.Exceptions.DeliveryNotFoundException;
import com.onlineperfumeshop.deliveryservice.Utils.HttpErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Component
public class ClientServiceClient {


    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String CLIENT_SERVICE_BASE_URL;

    public ClientServiceClient(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${app.client-service.host}") String clientServiceHost,
                               @Value("${app.client-service.port}") String clientServicePort) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.CLIENT_SERVICE_BASE_URL = "http://" + clientServiceHost + ":" + clientServicePort + "/api/v1/clients";
    }

    public ClientResponseModel getClientAggregate(String clientId) {
        ClientResponseModel clientResponseModel = new ClientResponseModel();
        try {
            String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
            clientResponseModel = restTemplate
                    .getForObject(url, ClientResponseModel.class);

        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return clientResponseModel;
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {


        if (ex.getStatusCode() == NOT_FOUND) {
            return new DeliveryNotFoundException(getErrorMessage(ex));
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








//        public ClientResponseModel[] getClients() {
//            ClientResponseModel clientResponseModel[] =null;
//            try {
//                String url = CLIENT_SERVICE_BASE_URL;
//                clientResponseModel = restTemplate
//                        .getForObject(url, ClientResponseModel[].class);
//
//
//                log.debug("5. In Get all Clients");
//            } catch (HttpClientErrorException ex) {
//                log.debug("5. update");
//                throw handleHttpClientException(ex);
//            }
//            return clientResponseModel;
//        }


}
