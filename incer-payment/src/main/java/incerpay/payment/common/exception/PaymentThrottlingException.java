package incerpay.payment.common.exception;

public class PaymentThrottlingException extends RuntimeException{
    public PaymentThrottlingException(String message) {
        super(message);
    }
}