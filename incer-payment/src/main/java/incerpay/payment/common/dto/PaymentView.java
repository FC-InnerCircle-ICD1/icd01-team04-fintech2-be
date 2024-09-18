package incerpay.payment.common.dto;

import incerpay.payment.domain.vo.PaymentState;
import org.javamoney.moneta.Money;

import java.util.UUID;

public record PaymentView(
        UUID paymentId,
        String sellerId,
        PaymentState state,
        Long amount
) {
    public PaymentView(UUID paymentId, String sellerId, PaymentState state, Money amount) {
        this(paymentId, sellerId, state, amount.getNumber().longValue());
    }
}
