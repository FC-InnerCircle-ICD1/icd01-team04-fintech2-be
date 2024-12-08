package incerpay.payment.domain.component;

import incerpay.payment.domain.entity.Payment;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    void save(Payment payment);

    Optional<Payment> findById(UUID paymentId);

    boolean existsBySellerIdAndCreatedAtAfter(String sellerId, Instant threshold);
}
