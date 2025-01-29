package incerpay.paygate.domain.log;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentApiLogRepository extends JpaRepository<PaymentApiLog, Long> {
}