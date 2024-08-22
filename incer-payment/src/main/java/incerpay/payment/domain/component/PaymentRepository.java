package incerpay.payment.domain.component;

import incerpay.payment.domain.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    UUID save(Payment payment);

    Optional<Payment> findById(UUID paymentId);
}
