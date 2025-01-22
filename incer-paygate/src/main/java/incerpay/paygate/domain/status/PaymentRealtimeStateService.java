package incerpay.paygate.domain.status;

import incerpay.paygate.presentation.dto.in.PaymentApproveCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentRealtimeStateService {

    private PaymentRealtimeStateRepository paymentRepository;

    public PaymentRealtimeState savePayment(PaymentRealtimeState payment) {
        log.debug("Saving payment state: {}", payment);
        PaymentRealtimeState saved = paymentRepository.save(payment);
        log.debug("Saved payment state: {}", saved);
        return saved;
    }


    public PaymentRealtimeState getPaymentRealTimeState(PaymentApproveCommand command) {
        String paymentId = command.paymentId().toString();
        String transactionId = command.transactionId().toString();
        return paymentRepository.findByPaymentIdAndTransactionId(paymentId, transactionId).orElseThrow(
                () -> new RuntimeException("해당하는 거래가 없습니다.")
        );
    }

}
