package incerpay.paygate.domain.status;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRealtimeStateRepository extends MongoRepository<PaymentRealtimeState, String> {

    Optional<PaymentRealtimeState> findByPaymentIdAndTransactionId(UUID transactionId, UUID paymentId);

}
