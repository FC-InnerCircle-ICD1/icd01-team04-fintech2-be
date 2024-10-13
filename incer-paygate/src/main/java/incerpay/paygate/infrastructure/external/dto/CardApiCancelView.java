package incerpay.paygate.infrastructure.external.dto;


import incerpay.paygate.domain.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiCancelView(
        PaymentState state,
        String paymentId,
        String transactionId,
        String canceledId,
        LocalDateTime requestAt,
        LocalDateTime canceledAt
) {}
