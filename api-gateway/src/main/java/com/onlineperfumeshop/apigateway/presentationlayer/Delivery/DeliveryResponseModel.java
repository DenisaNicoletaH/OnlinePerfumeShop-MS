package com.onlineperfumeshop.apigateway.presentationlayer.Delivery;


import com.onlineperfumeshop.apigateway.presentationlayer.Checkout.CheckoutResponseModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Client.ClientResponseModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductResponseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DeliveryResponseModel {



    private String deliveryId;
    private String checkoutId;
    private String warehouseLocation;

    private String shippingUpdate;

    private ProductResponseModel product;

    private CheckoutResponseModel checkoutInfo;

    private ClientResponseModel client;

    private String streetAddress;
    private String city;
    private String country;
    private String province;
    private String postalCode;


    private String phoneNumber;


    private LocalDate arrivalTime;

    private String countryCode;


}
