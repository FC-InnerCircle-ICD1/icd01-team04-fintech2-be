package incerpay.paygate.presentation.dto.in;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.presentation.dto.PaymentMethodDetails;

import java.math.BigDecimal;

public record PaymentRequestCommand(
     String customerId,
     String orderId,
     BigDecimal amount,
     PaymentType type,
     PaymentMethodDetails paymentMethodDetails
) {}

