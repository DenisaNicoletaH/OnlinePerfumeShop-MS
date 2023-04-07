package Utils.Exceptions;

public class DeliveryNotFoundException extends RuntimeException{

    public DeliveryNotFoundException(String message) { super(message); }

    public DeliveryNotFoundException(Throwable cause) { super(cause); }

    public DeliveryNotFoundException(String message, Throwable cause) { super(message, cause); }

}
