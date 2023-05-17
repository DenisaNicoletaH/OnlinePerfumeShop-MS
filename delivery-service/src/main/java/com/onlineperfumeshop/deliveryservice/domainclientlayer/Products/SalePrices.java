package com.onlineperfumeshop.deliveryservice.domainclientlayer.Products;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class SalePrices {


    private Double newPrices;
//
//    @SuppressWarnings("unused")
//    public SalePrices() {
//    }

    public SalePrices(@NotNull Double newPrices) {

        Objects.requireNonNull(this.newPrices = newPrices);


    }



    public @NotNull Double getNewPrices(){return newPrices;}

}
