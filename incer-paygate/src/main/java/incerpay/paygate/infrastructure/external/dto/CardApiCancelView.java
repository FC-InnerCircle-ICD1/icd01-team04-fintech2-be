package incerpay.paygate.infrastructure.external.dto;


import incerpay.paygate.domain.vo.PaymentState;

import java.time.LocalDateTime;

public record CardApiCancelView(
        String transactionId,
        long cancelAmount,
        LocalDateTime canceledAt,
        PaymentState cancelStatus
) {}
