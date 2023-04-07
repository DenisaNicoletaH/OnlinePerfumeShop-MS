package com.onlineperfumeshop.productsservice.datamapperlayer.Product;


import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductDiscountResponseModel;
import com.onlineperfumeshop.productsservice.presentationlayer.ProductInventoryResponseModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductDiscountResponseMapper {



    ProductDiscountResponseModel entitiesToResponseModel(DiscountResponseModel discount, List<ProductInventoryResponseModel> products);



}
