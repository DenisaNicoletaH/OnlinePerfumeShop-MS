package com.onlineperfumeshop.apigateway.presentationlayer.Products;

import com.onlineperfumeshop.apigateway.presentationlayer.Discount.DiscountResponseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDiscountResponseModel {

    private DiscountResponseModel discount;

   private ProductInventoryResponseModel[] products;

}
