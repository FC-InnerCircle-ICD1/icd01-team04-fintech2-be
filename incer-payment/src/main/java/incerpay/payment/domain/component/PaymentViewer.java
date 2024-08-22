package incerpay.payment.domain.component;

import incerpay.payment.domain.dto.PaymentStatusView;

import java.util.UUID;

public interface PaymentViewer {
    PaymentStatusView readPayment(UUID paymentId);
}
