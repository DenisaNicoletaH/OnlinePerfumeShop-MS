package com.onlineperfumeshop.productsservice.datamapperlayer.Product;

import com.onlineperfumeshop.productsservice.presentationlayer.Inventory.InventoryResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductInventoryResponseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductInventoryResponseMapper {





    ProductInventoryResponseModel entityToResponseModel(InventoryResponseModel inventory, ProductResponseModel product);


}


