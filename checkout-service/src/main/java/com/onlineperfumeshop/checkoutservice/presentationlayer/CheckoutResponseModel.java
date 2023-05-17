package com.onlineperfumeshop.checkoutservice.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckoutResponseModel {


    private String checkoutId;

    private Double amount;

    private Double taxes;

    private Double shipping;

    private LocalDate endOfSaleDate;
    private Double totalAmount;


    private String paymentType;

}
