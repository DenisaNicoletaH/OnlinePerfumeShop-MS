package com.onlineperfumeshop.apigateway.presentationlayer.Delivery;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryRequestModel {
    private String checkoutId;
    private String warehouseLocation;

    private String shippingUpdate;

    private String clientId;


    private String streetAddress;
    private String city;
    private String country;
    private String province;
    private String postalCode;

    private String phoneNumber;


    private String arrivalTime;
    private String countryCode;


}
