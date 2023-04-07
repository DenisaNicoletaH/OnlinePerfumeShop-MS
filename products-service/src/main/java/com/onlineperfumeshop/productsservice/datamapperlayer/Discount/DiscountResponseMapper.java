package com.onlineperfumeshop.productsservice.datamapperlayer.Discount;

import com.onlineperfumeshop.productsservice.datalayer.Discount.Discount;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscountResponseMapper {


    @Mapping(expression = "java(discount.getDiscountIdentifier().getDiscountId())", target = "discountId")
    DiscountResponseModel entityToResponseModel(Discount discount);

    List<DiscountResponseModel> entityListToResponseModelList(List<Discount> discountsList);
}

