package com.onlineperfumeshop.apigateway.presentationlayer.Products;


import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
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
