package incerpay.paygate.presentation.dto.out;

import incerpay.paygate.domain.vo.PaymentState;
import org.javamoney.moneta.Money;

import java.util.UUID;

public record ApiAdapterView(
        UUID paymentId,
        UUID transactionId,
        String sellerId,
        PaymentState state,
        Long amount
) {
    public ApiAdapterView(UUID paymentId, UUID transactionId, String sellerId, PaymentState state, Money amount) {
        this(paymentId, transactionId, sellerId, state, amount.getNumber().longValue());
    }
}
