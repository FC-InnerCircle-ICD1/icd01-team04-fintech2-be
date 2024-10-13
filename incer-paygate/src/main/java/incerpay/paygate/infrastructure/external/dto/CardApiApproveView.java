package incerpay.paygate.infrastructure.external.dto;

import incerpay.paygate.domain.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiApproveView(
        PaymentState state,
        Long paymentprice,
        String paymentId,
        String transactionId,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt
) {}

