package com.onlineperfumeshop.apigateway.presentationlayer.Products;

import com.onlineperfumeshop.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductInventoryResponseModel {


    private InventoryResponseModel inventory;


    private ProductResponseModel product;

}
