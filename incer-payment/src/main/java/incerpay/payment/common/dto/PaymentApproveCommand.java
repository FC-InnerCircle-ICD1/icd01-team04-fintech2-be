package incerpay.payment.common.dto;

import incerpay.payment.common.lib.request.RequestParameterException;

import java.util.UUID;

public record PaymentApproveCommand (
        UUID paymentId
){
    public PaymentApproveCommand {
        if (paymentId == null) {
            throw new RequestParameterException("paymentId is required");
        }
    }
}
