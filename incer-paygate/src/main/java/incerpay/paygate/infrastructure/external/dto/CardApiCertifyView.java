package incerpay.paygate.infrastructure.external.dto;

import incerpay.paygate.domain.vo.PaymentState;

public record CardApiCertifyView(
        PaymentState state,
        String customerName
) {}
