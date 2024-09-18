package incerpay.payment.common.dto;

import incerpay.payment.domain.vo.PaymentState;

import java.util.List;
import java.util.UUID;

public record PaymentDetailView(
        UUID paymentId,
        String sellerId,
        PaymentState state,
        Long amount,
        List<PaymentLedgerView> ledgers
) {

}
