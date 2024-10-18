package incerpay.paygate.infrastructure.external.dto;


import incerpay.paygate.domain.enumeration.PaymentState;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardApiCancelView(
        PaymentState state,
        UUID paymentId,
        UUID transactionId,
        String sellerId,
        String canceledId,
        LocalDateTime requestAt,
        LocalDateTime canceledAt
) {}
