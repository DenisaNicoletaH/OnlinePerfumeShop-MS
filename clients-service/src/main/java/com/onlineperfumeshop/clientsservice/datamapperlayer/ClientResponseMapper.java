package com.onlineperfumeshop.clientsservice.datamapperlayer;

import com.onlineperfumeshop.clientsservice.datalayer.Client;
import com.onlineperfumeshop.clientsservice.presentationlayer.ClientResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientResponseMapper {
    @Mapping(expression = "java(client.getClientIdentifier().getClientId())",  target = "clientId")
    @Mapping(expression = "java(client.getAddress().getStreetAddress())", target = "streetAddress" )
    @Mapping(expression = "java(client.getAddress().getCity())", target = "city" )
    @Mapping(expression = "java(client.getAddress().getProvince())", target = "province" )
    @Mapping(expression = "java(client.getAddress().getCountry())", target = "country" )
    @Mapping(expression = "java(client.getAddress().getPostalCode())", target = "postalCode" )
    @Mapping(expression = "java(client.getPhone().getPhoneNumber())", target = "phoneNumber" )
    @Mapping(expression = "java(client.getPhone().getCountryCode())", target = "countryCode" )
    ClientResponseModel entityToResponseModel(Client client);



    List<ClientResponseModel> entityListToResponseModelList(List<Client> clients);

}
