package incerpay.payment.payment.domain.dto;

import java.util.List;

public record PaymentListView (
        List<PaymentStateView> payments
){

}
