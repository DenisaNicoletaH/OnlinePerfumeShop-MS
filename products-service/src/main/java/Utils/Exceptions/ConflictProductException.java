package Utils.Exceptions;

public class ConflictProductException extends RuntimeException {

    public ConflictProductException() {
    }

    public ConflictProductException(String message) { super(message); }

    public ConflictProductException(Throwable cause) { super(cause); }

    public ConflictProductException(String message, Throwable cause) { super(message, cause); }
}



