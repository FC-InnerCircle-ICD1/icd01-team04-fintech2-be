package incerpay.paygate.domain.status;

import incerpay.paygate.presentation.dto.in.PaymentApproveCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentRealtimeStateService {

    private PaymentRealtimeStateRepository paymentRepository;

    public PaymentRealtimeState savePayment(PaymentRealtimeState payment) {
        return paymentRepository.save(payment);
    }

    public PaymentRealtimeState getPaymentRealTimeState(PaymentApproveCommand command) {
        UUID paymentId = command.paymentId();
        UUID transactionId = command.transactionId();
        return paymentRepository.findByPaymentIdAndTransactionId(paymentId, transactionId).orElseThrow(
                () -> new RuntimeException("해당하는 거래가 없습니다.")
        );
    }

}
