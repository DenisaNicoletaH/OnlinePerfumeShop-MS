package com.onlineperfumeshop.apigateway.businesslayer.Discount;

import com.onlineperfumeshop.apigateway.presentationlayer.Discount.DiscountRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Discount.DiscountResponseModel;

import java.util.List;

public interface DiscountService {
    DiscountResponseModel addDiscount(DiscountRequestModel discountRequestModel);

    DiscountResponseModel getDiscountByIdentifier(String discountId);

    DiscountResponseModel[] getDiscounts();

    void deleteDiscount(String discountId);
}
