package incerpay.paygate.presentation.dto.in;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.domain.vo.PaymentState;

import java.util.UUID;

public record PaymentApproveCommand(
        UUID paymentId,
        UUID transactionId,
        String sellerId,
        PaymentState state,
        PaymentType type,
        Long amount
) {}


