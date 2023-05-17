package com.onlineperfumeshop.deliveryservice.domainclientlayer.Checkout;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CheckoutResponseModel {


    private String checkoutId;

    private Double amount;

    private Double taxes;

    private Double shipping;

    private LocalDate endOfSaleDate;
    private Double totalAmount;

    private String productId;
    private String paymentType;

}
