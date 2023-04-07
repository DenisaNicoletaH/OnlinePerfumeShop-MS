package com.onlineperfumeshop.productsservice.businesslayer.Discount;


import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountRequestModel;
import com.onlineperfumeshop.productsservice.presentationlayer.Discount.DiscountResponseModel;

import java.util.List;

public interface DiscountService {
    DiscountResponseModel addDiscount(DiscountRequestModel discountRequestModel);

    DiscountResponseModel getDiscountByIdentifier(String discountId);

    List<DiscountResponseModel> getDiscounts();

    void deleteDiscount(String discountId);




}
