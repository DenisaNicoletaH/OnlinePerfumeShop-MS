package com.onlineperfumeshop.clientsservice.Utils.Exceptions;

public class ConflictClientException extends RuntimeException {


    public ConflictClientException(String message) { super(message); }

    public ConflictClientException(Throwable cause) { super(cause); }

    public ConflictClientException(String message, Throwable cause) { super(message, cause); }
}



