package com.onlineperfumeshop.productsservice.presentationlayer;

import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountResponseModel;
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

   private List<ProductInventoryResponseModel> products;

}
