package com.onlineperfumeshop.productsservice.datamapperlayer.Product;

import com.onlineperfumeshop.productsservice.datalayer.Product.Product;
import com.onlineperfumeshop.productsservice.presentationlayer.Product.ProductResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mapping(expression = "java(product.getProductIdentifier().getProductId())",  target = "productId")
    @Mapping(expression = "java(product.getDiscountIdentifier().getDiscountId())",  target = "discountId")
    @Mapping(expression = "java(product.getInventoryIdentifier().getInventoryId())",  target = "inventoryId")

    @Mapping(expression = "java(product.getPerfume().getScentType())", target = "scentType" )
    @Mapping(expression = "java(product.getPerfume().getDateProduced())", target = "dateProduced" )
    ProductResponseModel entityToResponseModel(Product product);

    List<ProductResponseModel> entityListToResponseModelList(List<Product> products);



}
