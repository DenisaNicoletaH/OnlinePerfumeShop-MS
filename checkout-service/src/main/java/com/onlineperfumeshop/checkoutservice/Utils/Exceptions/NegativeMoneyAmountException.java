package com.onlineperfumeshop.checkoutservice.Utils.Exceptions;

public class NegativeMoneyAmountException extends RuntimeException {
    public NegativeMoneyAmountException(String message) {
        super(message);
    }
}
