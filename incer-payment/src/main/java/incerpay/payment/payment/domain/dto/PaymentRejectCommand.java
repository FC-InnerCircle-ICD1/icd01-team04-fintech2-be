package incerpay.payment.payment.domain.dto;

import incerpay.payment.common.lib.request.RequestParameterException;

import java.util.UUID;

public record PaymentRejectCommand(
        UUID paymentId
) {
    public PaymentRejectCommand {
        if (paymentId == null) {
            throw new RequestParameterException("paymentId is required");
        }
    }
}
