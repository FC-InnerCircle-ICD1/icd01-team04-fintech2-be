package incerpay.paygate.presentation.dto.out;

import java.util.List;

public record PaymentStateListView(
        String sellerId,
        List<PaymentStateView> list
) {
}