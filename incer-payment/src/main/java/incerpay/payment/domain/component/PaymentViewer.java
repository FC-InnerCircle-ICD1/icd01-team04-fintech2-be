package incerpay.payment.domain.component;

import incerpay.payment.common.dto.PaymentDetailView;
import incerpay.payment.common.dto.PaymentListView;
import incerpay.payment.common.dto.PaymentView;

import java.util.UUID;

public interface PaymentViewer {
    PaymentView readPayment(UUID paymentId);

    PaymentListView readBySellerId(String sellerId);

    PaymentDetailView readDetailBySellerId(String sellerId, String paymentId);
}
