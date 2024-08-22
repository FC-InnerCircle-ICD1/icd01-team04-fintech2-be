package incerpay.payment.domain.component;

import incerpay.payment.domain.Payment;
import incerpay.payment.domain.dto.PaymentStatusView;

public interface PaymentViewer {
    PaymentStatusView readPayment(Payment payment);
}
