package com.onlineperfumeshop.deliveryservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeliveryRequestModel {
    private String checkoutId;
    private String warehouseLocation;

    private String shippingUpdate;

    private String clientId;


    private String productId;

    private String inventoryId;

    private String discountId;

    private String streetAddress;
    private String city;
    private String country;
    private String province;
    private String postalCode;

    private String phoneNumber;


    private String arrivalTime;
    private String countryCode;


}
