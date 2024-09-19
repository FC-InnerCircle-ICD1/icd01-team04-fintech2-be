package incerpay.payment.common.dto;

import incerpay.payment.common.lib.request.RequestParameterException;

import java.util.UUID;

public record PaymentConfirmCommand(
        UUID paymentId
){
    public PaymentConfirmCommand {
        if (paymentId == null) {
            throw new RequestParameterException("paymentId is required");
        }
    }
}
