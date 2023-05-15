package com.onlineperfumeshop.apigateway.businesslayer.Inventory;

import com.onlineperfumeshop.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductResponseModel;

import java.util.List;

public interface InventoryService {



    ProductResponseModel getProductByIdentifier(String productId);


    ProductResponseModel[] getProducts();


    void deleteProducts(String productId);

    void updateProduct(ProductRequestModel productRequestModel, String productId);


    InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);

    InventoryResponseModel getInventoryByInventoryIdentifier(String inventoryId);
    InventoryResponseModel [] getInventories();

    void updateInventory(InventoryRequestModel inventoryRequestModel, String inventoryId);
    void deleteInventory(String inventoryId);




    ProductResponseModel addProductToInventory(ProductRequestModel productRequestModel, String inventoryId);

    ProductResponseModel[] getProductsByInventoryIdentifier_InventoryId(String inventoryId);


    ProductResponseModel[] getProductByBrand(String brand);

    ProductResponseModel[] getProductByDiscountId(String discountId);








}
