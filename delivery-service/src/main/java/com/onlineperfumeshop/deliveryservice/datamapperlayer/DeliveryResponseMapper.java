package com.onlineperfumeshop.deliveryservice.datamapperlayer;

import com.onlineperfumeshop.deliveryservice.datalayer.Delivery;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryResponseMapper {

    @Mapping(expression = "java(delivery.getCheckoutIdentifier().getCheckoutId())",  target = "checkoutId")
    @Mapping(expression = "java(delivery.getDeliveryIdentifier().getDeliveryId())",  target = "deliveryId")
    @Mapping(expression = "java(delivery.getPhone().getPhoneNumber())",  target = "phoneNumber")
    @Mapping(expression = "java(delivery.getAddress().getStreetAddress())",  target = "streetAddress")
    @Mapping(expression = "java(delivery.getAddress().getCity())",  target = "city")
    @Mapping(expression = "java(delivery.getAddress().getCountry())",  target = "country")
    @Mapping(expression = "java(delivery.getAddress().getProvince())",  target = "province")
    @Mapping(expression = "java(delivery.getAddress().getPostalCode())",  target = "postalCode")
    @Mapping(expression = "java(delivery.getClientIdentifier().getClientId())",  target = "clientId")
    @Mapping(expression = "java(delivery.getArrivalTime())", target = "arrivalTime" )
    DeliveryResponseModel entityToResponseModel(Delivery delivery);

    List<DeliveryResponseModel> entitiesToResponseModel(List<Delivery> deliveries);

}
