package com.onlineperfumeshop.apigateway.businesslayer.Client;


import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientResponseModel;

import java.util.List;

public interface ClientService {


    ClientResponseModel addClient(ClientRequestModel clientRequestModel);

    ClientResponseModel getClientByIdentifier(String clientId);


    ClientResponseModel[] getClients();


    void updateClient(ClientRequestModel clientRequestModel, String clientId);

    void deleteClient(String clientId);
}


