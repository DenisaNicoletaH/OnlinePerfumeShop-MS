package com.onlineperfumeshop.checkoutservice.datalayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Embedded
    private CheckoutIdentifier checkoutIdentifier;
    private Double amount;

    private Double taxes;

    private Double shipping;

    private LocalDate endOfSale;

    @Embedded
    private PaymentMethod paymentMethod;

    @Embedded
    private ProductIdentifier productIdentifier;

    public Checkout() {
        this.checkoutIdentifier=new CheckoutIdentifier();

    }

    public Checkout(Double amount, Double taxes, Double shipping, LocalDate endOfSale,String paymentType,Double totalAmount) {
        checkoutIdentifier=new CheckoutIdentifier();
        this.amount = amount;
        this.taxes = taxes;
        this.shipping = shipping;
        this.endOfSale = endOfSale;
        this.paymentMethod=new PaymentMethod(paymentType,totalAmount);
    }


    public @NotNull Double getAmount() {
        return amount;
    }
    public @NotNull Double getTaxes() {
        return taxes;
    }
    public @NotNull Double getShipping() {
        return shipping;
    }
    public @NotNull LocalDate getEndOfSaleDate() {
        return endOfSale;
    }



}
