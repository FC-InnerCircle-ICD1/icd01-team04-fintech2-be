package incerpay.paygate.infrastructure.external.dto;

import incerpay.paygate.domain.enumeration.PaymentState;

import java.util.UUID;

public record IncerPaymentSuccessData(
        UUID paymentId,
        String sellerId,
        PaymentState state,
        Long price
) implements IncerPaymentApiDataView {

}