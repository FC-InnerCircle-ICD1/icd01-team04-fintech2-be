package incerpay.paygate.presentation.dto.in;

import incerpay.paygate.domain.enumeration.PaymentType;
import incerpay.paygate.presentation.dto.PaymentMethodDetails;

import java.math.BigDecimal;

public record PaymentRequestCommand(
     String sellerId,
     String orderId,
     BigDecimal price,
     PaymentType type,
     String successUrl,
     String failUrl,
     PaymentMethodDetails paymentMethodDetails
) {}

