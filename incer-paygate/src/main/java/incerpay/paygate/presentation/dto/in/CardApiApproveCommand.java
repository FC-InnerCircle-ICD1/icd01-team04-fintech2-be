package incerpay.paygate.presentation.dto.in;

public record CardApiApproveCommand(
        String customerId,
        int installmentPeriod,
        Long price,
        String cardNumber,
        String cvc,
        String expireDate,
        String cardCompany
) {}

