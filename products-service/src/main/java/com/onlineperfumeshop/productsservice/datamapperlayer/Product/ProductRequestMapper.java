package com.onlineperfumeshop.productsservice.datamapperlayer.Product;


import com.onlineperfumeshop.productsservice.datalayer.Product.Product;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productIdentifier",ignore = true)
    @Mapping(target = "inventoryIdentifier",ignore = true)
    @Mapping(target = "perfume",ignore = true)
    @Mapping(target = "discountIdentifier", ignore = true)
    Product entityToRequestModel(ProductRequestModel productRequestModel);
}