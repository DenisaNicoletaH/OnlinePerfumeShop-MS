package com.onlineperfumeshop.apigateway.presentationlayer.Discount;


import com.onlineperfumeshop.apigateway.presentationlayer.Products.SalePrices;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.SaleStatus;
import lombok.*;

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
