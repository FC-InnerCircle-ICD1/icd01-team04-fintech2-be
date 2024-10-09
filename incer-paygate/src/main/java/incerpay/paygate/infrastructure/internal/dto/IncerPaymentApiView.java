package incerpay.paygate.infrastructure.internal.dto;

public record IncerPaymentApiView(
        int resultCode,
        String resultMsg,
        IncerPaymentApiDataView data
) {}
