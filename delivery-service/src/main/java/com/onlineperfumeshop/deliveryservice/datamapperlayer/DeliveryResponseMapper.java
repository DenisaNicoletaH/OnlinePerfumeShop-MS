package com.onlineperfumeshop.deliveryservice.datamapperlayer;

import ch.qos.logback.core.net.server.Client;
import com.onlineperfumeshop.deliveryservice.datalayer.Delivery;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout.CheckoutResponseModel;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Clients.ClientResponseModel;
import com.onlineperfumeshop.deliveryservice.domainclientlayer.Products.ProductResponseModel;
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
    @Mapping(expression = "java(delivery.getArrivalTime())", target = "arrivalTime" )
    @Mapping(expression = "java(delivery.getPhone().getCountryCode())", target = "countryCode" )
    @Mapping(expression = "java(productResponseModel)", target = "product" )
    @Mapping(expression = "java(clientResponseModel)", target = "client" )
    @Mapping(expression = "java(checkoutResponseModel)", target = "checkoutInfo" )

    DeliveryResponseModel entityToResponseModel(Delivery delivery, ProductResponseModel productResponseModel, CheckoutResponseModel checkoutResponseModel, ClientResponseModel clientResponseModel);



    List<DeliveryResponseModel> entitiesToResponseModel(List<Delivery> deliveries);

}
