package com.onlineperfumeshop.apigateway.domainclientlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.ClientInvalidInputException;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.ConflictClientException;
import com.onlineperfumeshop.apigateway.Utils.Exceptions.Client.NotFoundException;
import com.onlineperfumeshop.apigateway.Utils.HttpErrorInfo;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientResponseModel;
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
                               @Value("${app.clients-service.host}") String clientServiceHost,
                               @Value("${app.clients-service.port}") String clientServicePort) {
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

        if(ex.getStatusCode() == CONFLICT){
            return new ConflictClientException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }
        if (ex.getStatusCode() == UNPROCESSABLE_ENTITY) {
            return new ClientInvalidInputException(getErrorMessage(ex));
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


    public ClientResponseModel addClient(ClientRequestModel clientRequestModel) {
        ClientResponseModel clientResponseModel=new ClientResponseModel();
        try {
            String url = CLIENT_SERVICE_BASE_URL  ;
            clientResponseModel = restTemplate.postForObject(url,clientRequestModel,ClientResponseModel.class);

            log.debug("5. Received in AddClient");
        } catch (HttpClientErrorException ex) {
            log.debug("5.");
            throw handleHttpClientException(ex);
        }
        return clientResponseModel;
    }




    public void updateClient(ClientRequestModel clientRequestModel, String clientId) {
        try {
            String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
            restTemplate.put(url, clientRequestModel);

            log.debug("5. Received in UpdateClient");
        } catch (HttpClientErrorException ex) {
            log.debug("5. update");
            throw handleHttpClientException(ex);
        }
    }


        public void deleteClient (String clientId) {
            try {
                String url = CLIENT_SERVICE_BASE_URL + "/" + clientId;
                restTemplate.delete(url);


                log.debug("5. in DeleteClient");
            } catch (HttpClientErrorException ex) {
                log.debug("5. delete");
                throw handleHttpClientException(ex);
            }
        }


        public ClientResponseModel[] getClients() {
            ClientResponseModel clientResponseModel[] =null;
            try {
                String url = CLIENT_SERVICE_BASE_URL;
                clientResponseModel = restTemplate
                        .getForObject(url, ClientResponseModel[].class);


                log.debug("5. In Get all Clients");
            } catch (HttpClientErrorException ex) {
                log.debug("5. update");
                throw handleHttpClientException(ex);
            }
            return clientResponseModel;
        }


}
