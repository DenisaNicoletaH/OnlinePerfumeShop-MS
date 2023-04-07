package com.onlineperfumeshop.productsservice.businesslayer.Inventory;




import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductDiscountResponseModel;

import java.util.List;

public interface InventoryService {


    ProductResponseModel getProductByIdentifier(String productId);


    List<ProductResponseModel> getProducts();


    void deleteProducts(String productId);

    ProductResponseModel updateProduct(ProductRequestModel productRequestModel, String productId);


  InventoryResponseModel addInventory(InventoryRequestModel inventoryRequestModel);

  InventoryResponseModel getInventoryByInventoryIdentifier(String inventoryId);
   List<InventoryResponseModel> getInventories();

    InventoryResponseModel updateInventory(InventoryRequestModel inventoryRequestModel, String inventoryId);
    void deleteInventory(String inventoryId);




    ProductResponseModel addProductToInventory(ProductRequestModel productRequestModel, String inventoryId);

    List<ProductResponseModel> getProductsByInventoryIdentifier_InventoryId(String inventoryId);


    List<ProductResponseModel> getProductByBrand(String brand);

    ProductDiscountResponseModel getProductByDiscountId(String discountId);






}
