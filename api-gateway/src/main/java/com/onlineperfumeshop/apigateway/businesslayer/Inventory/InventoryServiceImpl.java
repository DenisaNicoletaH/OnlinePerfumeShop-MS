package com.onlineperfumeshop.apigateway.businesslayer.Inventory;

import com.onlineperfumeshop.apigateway.domainclientlayer.DiscountServiceClient;
import com.onlineperfumeshop.apigateway.domainclientlayer.InventoryServiceClient;
import com.onlineperfumeshop.apigateway.domainclientlayer.ProductServiceClient;
import com.onlineperfumeshop.apigateway.presentationlayer.Inventory.InventoryRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Inventory.InventoryResponseModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Products.ProductResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{

    private InventoryServiceClient inventoryServiceClient;

    private DiscountServiceClient discountServiceClient;
 private ProductServiceClient productServiceClient;

    public InventoryServiceImpl(InventoryServiceClient inventoryServiceClient, DiscountServiceClient discountServiceClient,ProductServiceClient productServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
        this.discountServiceClient=discountServiceClient;
        this.productServiceClient=productServiceClient;
    }

    @Override
    public ProductResponseModel getProductByIdentifier(String productId) {
        return productServiceClient.getProductAggregate(productId);
    }

    @Override
    public ProductResponseModel[] getProducts() {
        return productServiceClient.getProducts();
    }

    @Override
    public void deleteProducts(String productId) {
        productServiceClient.deleteProduct(productId);
    }

    @Override
    public void updateProduct(ProductRequestModel productRequestModel, String productId) {
        productServiceClient.updateProduct(productRequestModel,productId);
    }

    @Override
    public InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel) {
        return inventoryServiceClient.addInventory(inventoryRequestModel);
    }



    @Override
    public InventoryResponseModel getInventoryByInventoryIdentifier(String inventoryId) {
        return inventoryServiceClient.getInventoryAggregate(inventoryId);
    }

    @Override
    public InventoryResponseModel[] getInventories() {
        return inventoryServiceClient.getInventories();

    }

    @Override
    public void updateInventory(InventoryRequestModel inventoryRequestModel, String inventoryId) {
        inventoryServiceClient.updateInventory(inventoryRequestModel, inventoryId);

    }

    @Override
    public void deleteInventory(String inventoryId) {
        inventoryServiceClient.deleteInventory(inventoryId);
    }



    @Override
    public ProductResponseModel addProductToInventory(ProductRequestModel productRequestModel, String inventoryId) {
        return inventoryServiceClient.addProductToInventory(productRequestModel,inventoryId);
    }

    //CHECK THIS
    @Override
    public ProductResponseModel[] getProductsByInventoryIdentifier_InventoryId(String inventoryId) {
        return inventoryServiceClient.getProductsByInventoryIdentifier_InventoryId(inventoryId);
    }


    //RECHECK THIS
    @Override
    public ProductResponseModel[] getProductByBrand(String brand) {
        return inventoryServiceClient.getProductByBrand(brand);
    }

    @Override
    public ProductResponseModel[] getProductByDiscountId(String discountId) {
        return productServiceClient.getProductByDiscountId(discountId);
    }
}
