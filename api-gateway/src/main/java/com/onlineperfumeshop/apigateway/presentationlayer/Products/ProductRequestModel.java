package com.onlineperfumeshop.apigateway.presentationlayer.Products;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequestModel {

    private String name;

    private String brand;

    private Double price;

    private String discountId;
    private String inventoryId;

    private String status;

    private String scentType;

    private LocalDate dateProduced;



}


