package com.onlineperfumeshop.deliveryservice.domainclientlayer.Products;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DiscountResponseModel {

    private String discountId;


    private Integer salePercent;

    private SaleStatus saleStatus;

    private SalePrices salePrices;


}
