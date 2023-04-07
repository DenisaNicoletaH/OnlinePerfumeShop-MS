package com.onlineperfumeshop.deliveryservice.datamapperlayer;

import com.onlineperfumeshop.deliveryservice.datalayer.Delivery;
import com.onlineperfumeshop.deliveryservice.presentationlayer.DeliveryRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deliveryIdentifier",ignore = true)
    @Mapping(target = "checkoutIdentifier", ignore = true)
    @Mapping(target = "clientIdentifier", ignore = true)
    @Mapping(expression = "java(deliveryRequestModel.getPhoneNumber())",  target = "phone.phoneNumber")
    @Mapping(expression = "java(deliveryRequestModel.getStreetAddress())",  target = "address.streetAddress")
    @Mapping(expression = "java(deliveryRequestModel.getCity())",  target = "address.city")
    @Mapping(expression = "java(deliveryRequestModel.getCountry())",  target = "address.country")
    @Mapping(expression = "java(deliveryRequestModel.getProvince())",  target = "address.province")
    @Mapping(expression = "java(deliveryRequestModel.getPostalCode())",  target = "address.postalCode")
    Delivery entityToRequestModel(DeliveryRequestModel deliveryRequestModel);

}
