package com.onlineperfumeshop.apigateway.businesslayer.Client;

import com.onlineperfumeshop.apigateway.domainclientlayer.ClientServiceClient;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientServiceClient clientServiceClient;


    public ClientServiceImpl(ClientServiceClient clientServiceClient) {
        this.clientServiceClient = clientServiceClient;
    }


    @Override
    public ClientResponseModel addClient(ClientRequestModel clientRequestModel) {
            return clientServiceClient.addClient(clientRequestModel);
    }


    @Override
    public ClientResponseModel getClientByIdentifier(String clientId) {
        return clientServiceClient.getClientAggregate(clientId);
    }

    //This is done
    @Override
    public ClientResponseModel[] getClients() {
        return clientServiceClient.getClients();
    }

    @Override
    public void updateClient(ClientRequestModel clientRequestModel, String clientId) {
        clientServiceClient.updateClient(clientRequestModel, clientId);
    }


    @Override
    public void deleteClient(String clientId) {
        clientServiceClient.deleteClient(clientId);

    }
}
