package incerpay.paygate.infrastructure.internal.dto;

import java.util.List;

public record IncerPaymentApiListView(
        int resultCode,
        String resultMsg,
        List<IncerPaymentApiDataView> payments
) {}