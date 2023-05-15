package com.onlineperfumeshop.apigateway.businesslayer.Checkout;

import com.onlineperfumeshop.apigateway.presentationlayer.Checkout.CheckoutRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Checkout.CheckoutResponseModel;

import java.util.List;

public interface CheckoutService {


    CheckoutResponseModel addCheckout(CheckoutRequestModel checkoutRequestModel);

    CheckoutResponseModel getCheckoutByIdentifier(String checkoutId);

    CheckoutResponseModel[] getCheckouts();

    void deleteCheckout(String checkoutId);

    void updateCheckout(CheckoutRequestModel checkoutRequestModel, String checkoutId);

}
