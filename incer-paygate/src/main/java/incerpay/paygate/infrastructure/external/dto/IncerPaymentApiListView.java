package incerpay.paygate.infrastructure.external.dto;

import java.util.List;

public record IncerPaymentApiListView(
        int resultCode,
        String resultMsg,
        List<IncerPaymentApiDataView> payments
) {}