package incerpay.payment.presentation;

public record PaymentResponse(
        Object body
) {

    public static PaymentResponse ok(Object body) {
        return new PaymentResponse(body);
    }
}
