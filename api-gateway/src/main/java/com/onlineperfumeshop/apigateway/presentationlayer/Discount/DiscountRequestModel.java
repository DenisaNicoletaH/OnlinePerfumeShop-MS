package com.onlineperfumeshop.apigateway.presentationlayer.Discount;


import com.onlineperfumeshop.apigateway.presentationlayer.Products.SalePrices;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.SaleStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountRequestModel {

    private Integer salePercent;


    private SalePrices salePrices;


    private SaleStatus saleStatus;

}
