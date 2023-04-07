package com.onlineperfumeshop.productsservice.presentationlayer.Product;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponseModel {



    private String productId;

    private String discountId;
    private String inventoryId;

    private String name;

    private String brand;

    private Double price;

    private String scentType;

    private Date dateProduced;




}
