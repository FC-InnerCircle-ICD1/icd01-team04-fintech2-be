package incerpay.paygate.presentation.dto.in;

public record CardApiApproveCommand(
        int price,
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany,
        int installmentPeriod,
        String customerId,
        String paymentType
) {}

