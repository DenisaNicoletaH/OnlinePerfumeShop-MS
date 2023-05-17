package com.onlineperfumeshop.apigateway.businesslayer.Discount;

import com.onlineperfumeshop.apigateway.domainclientlayer.DiscountServiceClient;
import com.onlineperfumeshop.apigateway.presentationlayer.Discount.DiscountRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Discount.DiscountResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService{

    private DiscountServiceClient discountServiceClient;


    public DiscountServiceImpl(DiscountServiceClient discountServiceClient) {
        this.discountServiceClient = discountServiceClient;
    }

    @Override
    public DiscountResponseModel addDiscount(DiscountRequestModel discountRequestModel) {
        return discountServiceClient.addDiscount(discountRequestModel);
    }

    @Override
    public DiscountResponseModel getDiscountByIdentifier(String discountId) {
        return discountServiceClient.getDiscountsAggregate(discountId);
    }

    @Override
    public DiscountResponseModel[] getDiscounts() {
        return discountServiceClient.getDiscounts();
    }

    @Override
    public void deleteDiscount(String discountId){
        discountServiceClient.deleteDiscount(discountId);

    }
}
