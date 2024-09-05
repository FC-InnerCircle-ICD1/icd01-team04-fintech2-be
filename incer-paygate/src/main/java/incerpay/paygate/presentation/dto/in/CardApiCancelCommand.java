package incerpay.paygate.presentation.dto.in;

import java.util.UUID;

public record CardApiCancelCommand(
        String customerId,
        UUID paymendId,
        UUID transactionId,
        String paymentType
) {}

