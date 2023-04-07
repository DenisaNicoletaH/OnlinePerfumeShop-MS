package com.onlineperfumeshop.productsservice.datalayer.Product;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ProductIdentifier {

    private String productId;


    public ProductIdentifier() {
        this.productId = UUID.randomUUID().toString();
    }

    public ProductIdentifier(String productId) {
        this.productId = productId;
    }

    public String getProductId() {return this.productId;}





}

