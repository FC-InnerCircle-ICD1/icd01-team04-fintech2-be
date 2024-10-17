package incerpay.paygate.presentation.dto.in;

import java.util.UUID;

public record CardApiCancelCommand(
        String sellerId,
        UUID paymentId,
        UUID transactionId
) {}

