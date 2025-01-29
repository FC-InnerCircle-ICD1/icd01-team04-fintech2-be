package incerpay.paygate.domain.status;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRealtimeStateRepository extends MongoRepository<PaymentRealtimeState, String> {

    Optional<PaymentRealtimeState> findByPaymentIdAndTransactionId(String paymentId, String transactionId);
}
