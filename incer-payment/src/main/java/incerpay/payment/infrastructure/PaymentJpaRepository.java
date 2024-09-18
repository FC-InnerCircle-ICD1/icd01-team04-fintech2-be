package incerpay.payment.infrastructure;

import incerpay.payment.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<Payment, UUID> {

}
