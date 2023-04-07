package com.onlineperfumeshop.productsservice.presentationlayer.Product;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequestModel {

    private String name;

    private String brand;

    private Double price;

    private String discountId;

    private String status;

    private String scentType;

    private Date dateProduced;



}


