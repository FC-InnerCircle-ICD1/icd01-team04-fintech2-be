package incerpay.paygate.infrastructure.external.dto;

import incerpay.paygate.domain.vo.PaymentState;

import java.time.LocalDateTime;

public record CardApiApproveView(
        PaymentState state,
        LocalDateTime requestedAt,
        Long paymentAmount,
        String paymentId,
        String transactionId
) {}

