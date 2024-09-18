package incerpay.payment.common.dto;

import java.util.List;

public record PaymentListView (
        List<PaymentView> payments
){

}
