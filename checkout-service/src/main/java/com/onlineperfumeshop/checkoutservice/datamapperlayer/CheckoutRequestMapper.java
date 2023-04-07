package com.onlineperfumeshop.checkoutservice.datamapperlayer;

import com.onlineperfumeshop.checkoutservice.datalayer.Checkout;
import com.onlineperfumeshop.checkoutservice.presentationlayer.CheckoutRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckoutRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checkoutIdentifier",ignore = true)
    @Mapping(target = "paymentMethod",ignore = true)
    @Mapping(target = "productIdentifier", ignore = true)
    @Mapping(expression = "java(checkoutRequestModel.getEndOfSaleDate())",target = "endOfSale")
    Checkout entityToRequestModel(CheckoutRequestModel checkoutRequestModel);

}

