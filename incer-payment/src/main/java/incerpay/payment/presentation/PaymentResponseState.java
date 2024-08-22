package incerpay.payment.presentation;

public enum PaymentResponseState {
    OK(200),
    FAIL(400),
    ERROR(500);

    private final Integer statusCode;
    
    PaymentResponseState(int statusCode) {
        this.statusCode = statusCode;
    }

    public Integer statusCode() {
        return statusCode;
    }
}
