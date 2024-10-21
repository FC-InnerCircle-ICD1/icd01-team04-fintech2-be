package incerpay.paygate.presentation.dto.in;

import java.util.UUID;

public record IncerPaymentApiApproveCommand(
        UUID paymentId
) {}
