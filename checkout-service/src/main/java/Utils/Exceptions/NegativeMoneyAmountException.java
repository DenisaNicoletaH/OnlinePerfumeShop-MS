package Utils.Exceptions;

public class NegativeMoneyAmountException extends RuntimeException{
    public NegativeMoneyAmountException() {
    }

    public NegativeMoneyAmountException(String message) {
        super(message);
    }

    public NegativeMoneyAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeMoneyAmountException(Throwable cause) {
        super(cause);
    }
}
