package incerpay.payment.payment.domain.component;

import incerpay.payment.payment.domain.dto.PaymentListView;
import incerpay.payment.payment.domain.dto.PaymentStateView;

import java.util.UUID;

public interface PaymentViewer {
    PaymentStateView readPayment(UUID paymentId);

    PaymentListView readBySellerId(String sellerId);
}
