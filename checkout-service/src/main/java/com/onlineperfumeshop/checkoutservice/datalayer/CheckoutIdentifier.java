package com.onlineperfumeshop.checkoutservice.datalayer;

import java.util.UUID;

public class CheckoutIdentifier {

    private String checkoutId;

    public CheckoutIdentifier() {
        this.checkoutId = UUID.randomUUID().toString();
    }

    public CheckoutIdentifier(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getCheckoutId() {return this.checkoutId;}
}
