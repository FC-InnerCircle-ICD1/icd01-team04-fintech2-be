package incerpay.paygate.infrastructure.external.dto;

import java.util.List;

public record IncerPaymentApiListView(
        List<IncerPaymentApiView> paymentId
) {}