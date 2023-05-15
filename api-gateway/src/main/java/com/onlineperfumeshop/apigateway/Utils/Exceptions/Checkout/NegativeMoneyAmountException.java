package com.onlineperfumeshop.apigateway.Utils.Exceptions.Checkout;

public class NegativeMoneyAmountException extends RuntimeException {
    public NegativeMoneyAmountException(String message) {
        super(message);
    }
}
