package com.onlineperfumeshop.productsservice.presentationlayer.Discount;

import com.onlineperfumeshop.productsservice.datalayer.Discount.SalePrices;
import com.onlineperfumeshop.productsservice.datalayer.Discount.SaleStatus;
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
