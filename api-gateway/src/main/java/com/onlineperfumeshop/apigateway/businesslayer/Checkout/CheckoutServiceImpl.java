package com.onlineperfumeshop.apigateway.businesslayer.Checkout;

import com.onlineperfumeshop.apigateway.domainclientlayer.CheckoutServiceClient;
import com.onlineperfumeshop.apigateway.presentationlayer.Checkout.CheckoutRequestModel;
import com.onlineperfumeshop.apigateway.presentationlayer.Checkout.CheckoutResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CheckoutServiceImpl implements CheckoutService{

private final CheckoutServiceClient checkoutServiceClient;

    public CheckoutServiceImpl(CheckoutServiceClient checkoutServiceClient) {
        this.checkoutServiceClient = checkoutServiceClient;
    }

    @Override
    public CheckoutResponseModel addCheckout(CheckoutRequestModel checkoutRequestModel) {
        return checkoutServiceClient.addCheckout(checkoutRequestModel);
    }

    @Override
    public CheckoutResponseModel getCheckoutByIdentifier(String checkoutId) {
        return checkoutServiceClient.getCheckoutAggregate(checkoutId);
    }

    @Override
    public CheckoutResponseModel[] getCheckouts() {
        return checkoutServiceClient.getCheckouts();
    }

    @Override
    public void deleteCheckout(String checkoutId) {
    checkoutServiceClient.deleteCheckout(checkoutId);
    }

    @Override
    public void updateCheckout(CheckoutRequestModel checkoutRequestModel, String checkoutId) {
        checkoutServiceClient.updateCheckout(checkoutRequestModel, checkoutId);
    }
}
