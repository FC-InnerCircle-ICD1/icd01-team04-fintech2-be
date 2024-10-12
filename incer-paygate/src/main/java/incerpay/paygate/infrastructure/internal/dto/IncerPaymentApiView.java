package incerpay.paygate.infrastructure.internal.dto;

import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;

public record IncerPaymentApiView(
        int resultCode,
        String resultMsg,
        IncerPaymentApiDataView data
) {}
