package com.onlineperfumeshop.productsservice.datamapperlayer.Discount;


import com.onlineperfumeshop.productsservice.datalayer.Discount.Discount;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscountRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "discountIdentifier", ignore = true)
    Discount requestModelToEntity(DiscountRequestModel requestModel);
}

