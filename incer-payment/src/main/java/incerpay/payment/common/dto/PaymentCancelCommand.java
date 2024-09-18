package incerpay.payment.common.dto;

import incerpay.payment.common.lib.request.RequestParameterException;

import java.util.UUID;

public record PaymentCancelCommand(
        UUID paymentId
) {
    public PaymentCancelCommand {
        if (paymentId == null) {
            throw new RequestParameterException("paymentId is required");
        }
    }
}
