package incerpay.payment.payment.domain.dto;

import incerpay.payment.payment.domain.vo.PaymentState;
import org.javamoney.moneta.Money;

import java.util.UUID;

public record PaymentStateView(
        UUID paymentId,
        String sellerId,
        PaymentState state,
        Long amount
) {
    public PaymentStateView(UUID paymentId, String sellerId, PaymentState state, Money amount) {
        this(paymentId, sellerId, state, amount.getNumber().longValue());
    }
}
