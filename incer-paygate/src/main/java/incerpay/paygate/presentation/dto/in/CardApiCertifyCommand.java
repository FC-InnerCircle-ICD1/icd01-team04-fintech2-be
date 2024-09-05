package incerpay.paygate.presentation.dto.in;

public record CardApiCertifyCommand(
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany,
        String customerId
) {}

