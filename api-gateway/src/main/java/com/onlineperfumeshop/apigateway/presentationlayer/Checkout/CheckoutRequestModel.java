package com.onlineperfumeshop.apigateway.presentationlayer.Checkout;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckoutRequestModel {


    private Double amount;

    private Double taxes;

    private Double shipping;

    private Date currentDate;

    private String paymentType;

    private LocalDate endOfSaleDate;
    private Double totalAmount;

    private String productId;


}

