package incerpay.payment.payment.domain.component;

import incerpay.payment.payment.domain.entity.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    void save(Payment payment);

    Optional<Payment> findById(UUID paymentId);
}
