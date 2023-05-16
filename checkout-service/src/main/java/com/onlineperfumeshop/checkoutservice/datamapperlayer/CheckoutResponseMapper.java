package com.onlineperfumeshop.checkoutservice.datamapperlayer;


import com.onlineperfumeshop.checkoutservice.datalayer.Checkout;
import com.onlineperfumeshop.checkoutservice.presentationlayer.CheckoutResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckoutResponseMapper {

    @Mapping(expression = "java(checkout.getCheckoutIdentifier().getCheckoutId())",  target = "checkoutId")
    @Mapping(expression = "java(checkout.getProductIdentifier().getProductId())",  target = "productId")
    @Mapping(expression = "java(checkout.getPaymentMethod().getPaymentType())", target = "paymentType" )
    @Mapping(expression = "java(checkout.getPaymentMethod().getTotalAmount())", target = "totalAmount" )
    CheckoutResponseModel entityToResponseModel(Checkout checkout);

    List<CheckoutResponseModel> entitiesToResponseModel(List<Checkout> checkouts);

}
