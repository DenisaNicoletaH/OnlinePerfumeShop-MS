package com.onlineperfumeshop.apigateway.presentationlayer.Delivery;


import lombok.*;

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

    private String clientId;


    private String streetAddress;
    private String city;
    private String country;
    private String province;
    private String postalCode;


    private String phoneNumber;


    private LocalDate arrivalTime;

    private String countryCode;


}
