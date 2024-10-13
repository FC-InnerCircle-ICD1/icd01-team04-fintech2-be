package incerpay.paygate.presentation.dto.out;

import incerpay.paygate.domain.enumeration.PaymentState;
import org.javamoney.moneta.Money;

import java.util.UUID;

public record PaymentStateView(
        UUID paymentId,
        UUID transactionId,
        String sellerId,
        PaymentState state,
        Long price
) {
    public PaymentStateView(UUID paymentId, UUID transactionId, String sellerId, PaymentState state, Money price) {
        this(paymentId, transactionId, sellerId, state, price.getNumber().longValue());
    }
}
