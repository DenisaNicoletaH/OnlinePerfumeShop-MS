package Utils.Exceptions;

public class ClientInvalidInputException extends RuntimeException{

    public ClientInvalidInputException() {}

    public ClientInvalidInputException(String message) { super(message); }

    public ClientInvalidInputException(Throwable cause) { super(cause); }

    public ClientInvalidInputException(String message, Throwable cause) { super(message, cause); }
}