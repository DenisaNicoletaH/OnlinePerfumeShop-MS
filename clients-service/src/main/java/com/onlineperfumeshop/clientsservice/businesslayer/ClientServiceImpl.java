package com.onlineperfumeshop.clientsservice.businesslayer;


import Utils.Exceptions.ConflictClientException;
import Utils.Exceptions.NotFoundException;
import com.onlineperfumeshop.clientsservice.datalayer.Address;
import com.onlineperfumeshop.clientsservice.datalayer.Client;
import com.onlineperfumeshop.clientsservice.datalayer.ClientRepository;
import com.onlineperfumeshop.clientsservice.datalayer.Phone;
import com.onlineperfumeshop.clientsservice.datamapperlayer.ClientRequestMapper;
import com.onlineperfumeshop.clientsservice.datamapperlayer.ClientResponseMapper;
import com.onlineperfumeshop.clientsservice.presentationlayer.ClientRequestModel;
import com.onlineperfumeshop.clientsservice.presentationlayer.ClientResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {


    private ClientRepository clientRepository;
    private ClientResponseMapper clientResponseMapper;
    private ClientRequestMapper clientRequestMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientResponseMapper clientResponseMapper, ClientRequestMapper clientRequestMapper) {
        this.clientRepository = clientRepository;
        this.clientResponseMapper = clientResponseMapper;
        this.clientRequestMapper = clientRequestMapper;
    }

    //add client
    @Override
    public ClientResponseModel addClient(ClientRequestModel clientRequestModel) {
        if (clientRepository.existsByFirstNameAndLastName(clientRequestModel.getFirstName(), clientRequestModel.getLastName())) {
            throw new ConflictClientException("A client with the name : " + clientRequestModel.getFirstName() + " " + clientRequestModel.getLastName() + " already exists !");
        }
        Client client = clientRequestMapper.entityToRequestModel(clientRequestModel);
        Address address = new Address(clientRequestModel.getStreetAddress(), clientRequestModel.getCity(),
                clientRequestModel.getProvince(), clientRequestModel.getCountry(), clientRequestModel.getPostalCode());
        client.setAddress(address);
        Phone phone = new Phone(clientRequestModel.getPhoneNumber(), clientRequestModel.getCountryCode());
        client.setPhone(phone);

        //CHANGES
        Boolean invalid=clientRepository.existsClientsByFirstNameAndLastNameAndEmailAddress(clientRequestModel.getFirstName(), clientRequestModel.getLastName(), clientRequestModel.getEmailAddress());
        if(invalid){
            throw new ConflictClientException("Invalid FirstName,LastName and Email" + clientRequestModel.getFirstName() + " " + clientRequestModel.getLastName() +" "+  clientRequestModel.getEmailAddress());

        }else {
            Client newClient = clientRepository.save(client);
            ClientResponseModel clientResponse = clientResponseMapper.entityToResponseModel(newClient);
            return clientResponse;
        }
        //-------------
    }

    //get clientId
    @Override
    public ClientResponseModel getClientByIdentifier(String clientId) {
        return clientResponseMapper.entityToResponseModel(clientRepository.findByClientIdentifier_ClientId(clientId));

    }


    //get all clients
    @Override
    public List<ClientResponseModel> getClients() {
        return clientResponseMapper.entityListToResponseModelList(clientRepository.findAll());

    }


    @Override
    public ClientResponseModel updateClient(ClientRequestModel clientRequestModel, String clientId) {
        Client client = clientRequestMapper.entityToRequestModel(clientRequestModel);
        Client existingClient = clientRepository.findByClientIdentifier_ClientId(clientId);
        if (existingClient == null) {
            throw new NotFoundException("No client was found with id  : " + clientId);
        }
        client.setId(existingClient.getId());
        client.setClientIdentifier(existingClient.getClientIdentifier());
        Address address = new Address(clientRequestModel.getStreetAddress(), clientRequestModel.getCity(),
                clientRequestModel.getProvince(), clientRequestModel.getCountry(), clientRequestModel.getPostalCode());
        client.setAddress(address);

        Phone phone = new Phone(clientRequestModel.getPhoneNumber(), clientRequestModel.getCountryCode());
        client.setPhone(phone);
        client.setClientIdentifier(existingClient.getClientIdentifier());
        client.setId(existingClient.getId());





            Client updatedClient = clientRepository.save(client);
            ClientResponseModel clientResponse = clientResponseMapper.entityToResponseModel(updatedClient);
            return clientResponse;

        }

    @Override
    public void deleteClient(String clientId) {
        Client existingClient = clientRepository.findByClientIdentifier_ClientId(clientId);

        if (existingClient == null) {
            throw new NotFoundException("No client was found with id  : " + clientId);
        }

        clientRepository.delete(existingClient);

    }
}
