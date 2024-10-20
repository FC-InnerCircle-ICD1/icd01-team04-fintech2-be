package incerpay.paygate.infrastructure.internal.dto;

import incerpay.paygate.infrastructure.external.dto.IncerPaymentApiDataView;

import java.util.List;

public record IncerPaymentApiListView(
        int resultCode,
        String resultMsg,
        Data data
) {
    public record Data(
            List<IncerPaymentApiDataView> payments
    ) {}
}