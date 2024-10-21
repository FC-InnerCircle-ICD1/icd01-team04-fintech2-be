package incerpay.paygate.infrastructure.external.dto;

import incerpay.paygate.domain.enumeration.PaymentState;

import java.time.LocalDateTime;
import java.util.UUID;

public record CardApiApproveView(
        PaymentState state,
        Long price,
        String sellerId,
        UUID paymentId,
        UUID transactionId,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt
) {}

