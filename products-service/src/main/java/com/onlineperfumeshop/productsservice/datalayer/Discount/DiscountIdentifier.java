package com.onlineperfumeshop.productsservice.datalayer.Discount;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class DiscountIdentifier {


    private String discountId;

   public DiscountIdentifier() {
        this.discountId = UUID.randomUUID().toString();
    }


   public DiscountIdentifier(String discountId) {
        this.discountId = discountId;
    }




    public String getDiscountId() {
        return this.discountId;
    }
}


