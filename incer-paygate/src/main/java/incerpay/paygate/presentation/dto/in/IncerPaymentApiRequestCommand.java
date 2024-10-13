package incerpay.paygate.presentation.dto.in;

import java.time.LocalDateTime;

public record IncerPaymentApiRequestCommand(
        String sellerId,
        Long price,
        LocalDateTime expiredAt
) {}