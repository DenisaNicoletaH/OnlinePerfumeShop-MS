package com.onlineperfumeshop.clientsservice.businesslayer;

import com.onlineperfumeshop.clientsservice.presentationlayer.ClientRequestModel;
import com.onlineperfumeshop.clientsservice.presentationlayer.ClientResponseModel;

import java.util.List;

public interface ClientService {

    ClientResponseModel addClient(ClientRequestModel clientRequestModel);

    ClientResponseModel getClientByIdentifier(String clientId);


    List<ClientResponseModel> getClients();


    ClientResponseModel updateClient(ClientRequestModel clientRequestModel, String clientId);

    void deleteClient(String clientId);
}
