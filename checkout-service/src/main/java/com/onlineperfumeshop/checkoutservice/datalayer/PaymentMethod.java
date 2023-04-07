package com.onlineperfumeshop.checkoutservice.datalayer;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable

public class PaymentMethod {


    private String paymentType;


    private Double totalAmount;


    @SuppressWarnings("unused")
    public PaymentMethod() {
    }

    public PaymentMethod(@NotNull String paymentType, @NotNull Double totalAmount) {

        Objects.requireNonNull(this.paymentType = paymentType);
        Objects.requireNonNull(this.totalAmount = totalAmount);

    }
    public @NotNull String getPaymentType(){return paymentType;}

    public @NotNull Double getTotalAmount() {return totalAmount;}



}
