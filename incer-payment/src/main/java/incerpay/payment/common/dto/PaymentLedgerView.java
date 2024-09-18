package incerpay.payment.common.dto;

import incerpay.payment.domain.vo.PaymentState;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public record PaymentLedgerView (
        UUID paymentId,
        PaymentState state,
        Long amount,
        LocalDateTime registeredAt
){
    public PaymentLedgerView(UUID paymentId, PaymentState state, Long amount, Instant registeredAt) {
        this(paymentId, state, amount, LocalDateTime.ofInstant(registeredAt, ZoneId.of("Asia/Seoul")));
    }
}
