package incerpay.paygate.presentation.dto.in;

public record PaymentRequestCommand(
    String cardNumber,
    String cvc,
    String expireDate,
    String cardCompany,
    String customerId
) {}

