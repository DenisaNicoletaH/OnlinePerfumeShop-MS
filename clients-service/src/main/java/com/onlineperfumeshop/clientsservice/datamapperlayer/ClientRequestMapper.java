package com.onlineperfumeshop.clientsservice.datamapperlayer;


import com.onlineperfumeshop.clientsservice.datalayer.Client;
import com.onlineperfumeshop.clientsservice.presentationlayer.ClientRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper {


    @Mapping(target = "clientIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "phone", ignore = true)
    Client entityToRequestModel(ClientRequestModel clientRequestModel);









}
