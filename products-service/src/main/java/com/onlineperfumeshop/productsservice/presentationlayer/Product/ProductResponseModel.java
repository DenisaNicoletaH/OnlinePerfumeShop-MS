package com.onlineperfumeshop.productsservice.presentationlayer.Product;


import com.onlineperfumeshop.productsservice.datalayer.Product.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
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

    private String status;

    private String scentType;

    private LocalDate dateProduced;




}
