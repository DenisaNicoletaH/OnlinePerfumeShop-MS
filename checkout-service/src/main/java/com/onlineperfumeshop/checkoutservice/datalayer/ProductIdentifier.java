package com.onlineperfumeshop.checkoutservice.datalayer;

import java.util.UUID;

public class ProductIdentifier {

    private final String productId;


    public ProductIdentifier() {
        this.productId = UUID.randomUUID().toString();
    }

    public ProductIdentifier(String productId) {
        this.productId = productId;
    }

    public String getProductId() {return this.productId;}





}



