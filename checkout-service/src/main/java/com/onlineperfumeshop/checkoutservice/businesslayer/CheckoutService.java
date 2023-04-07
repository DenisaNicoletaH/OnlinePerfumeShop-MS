package com.onlineperfumeshop.checkoutservice.businesslayer;

import com.onlineperfumeshop.checkoutservice.presentationlayer.CheckoutRequestModel;
import com.onlineperfumeshop.checkoutservice.presentationlayer.CheckoutResponseModel;

import java.util.List;

public interface CheckoutService {

    CheckoutResponseModel addCheckout(CheckoutRequestModel checkoutRequestModel);

    CheckoutResponseModel getCheckoutByIdentifier(String checkoutId);

    List<CheckoutResponseModel> getCheckouts();

    void deleteClient(String checkoutId);

    CheckoutResponseModel updateCheckout(CheckoutRequestModel checkoutRequestModel, String checkoutId);
}
