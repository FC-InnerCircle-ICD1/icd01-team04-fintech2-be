package incerpay.paygate.presentation.dto.in;

import java.util.UUID;

public record PaymentCancelCommand(
    UUID paymentId,
    UUID transactionId
) {}

