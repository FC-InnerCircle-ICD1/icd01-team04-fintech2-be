package incerpay.payment.presentation;

public record PaymentResponse(
        PaymentResponseState state,
        Object body
) {

    public static PaymentResponse ok(Object body) {
        return new PaymentResponse(PaymentResponseState.OK, body);
    }

    public static PaymentResponse fail(Object body) {
        return new PaymentResponse(PaymentResponseState.FAIL, body);
    }

    public static PaymentResponse error(Object body) {
        return new PaymentResponse(PaymentResponseState.ERROR, body);
    }

}
