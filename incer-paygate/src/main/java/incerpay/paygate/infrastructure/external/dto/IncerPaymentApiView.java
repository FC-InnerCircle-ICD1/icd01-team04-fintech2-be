package incerpay.paygate.infrastructure.external.dto;

public record IncerPaymentApiView(
        int resultCode,
        String resultMsg,
        IncerPaymentApiDataView data
) {}
