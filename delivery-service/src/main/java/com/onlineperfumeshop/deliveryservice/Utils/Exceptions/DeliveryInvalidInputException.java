package com.onlineperfumeshop.deliveryservice.Utils.Exceptions;

public class DeliveryInvalidInputException extends RuntimeException{

    public DeliveryInvalidInputException() {}

    public DeliveryInvalidInputException(String message) { super(message); }

    public DeliveryInvalidInputException(Throwable cause) { super(cause); }

    public DeliveryInvalidInputException(String message, Throwable cause) { super(message, cause); }
}