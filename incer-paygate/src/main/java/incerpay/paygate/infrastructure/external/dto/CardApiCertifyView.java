package incerpay.paygate.infrastructure.external.dto;

import incerpay.paygate.domain.enumeration.PaymentState;

import java.time.LocalDateTime;

public record CardApiCertifyView(
        PaymentState state,
        String cardNumber,
        String customerName,
        String certifyNumber,
        LocalDateTime requestAt,
        LocalDateTime certifiedAt
) {}

