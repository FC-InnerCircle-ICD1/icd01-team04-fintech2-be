package incerpay.payment.domain.component;

import incerpay.payment.domain.Payment;

import java.util.Optional;

public interface PaymentRepository {
    void save(Payment payment);

    Optional<Payment> findById(String paymentId);
}
