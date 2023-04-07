package Utils.Exceptions;

public class ProductInvalidInputException extends RuntimeException{

    public ProductInvalidInputException() {}

    public ProductInvalidInputException(String message) { super(message); }

    public ProductInvalidInputException(Throwable cause) { super(cause); }

    public ProductInvalidInputException(String message, Throwable cause) { super(message, cause); }
}