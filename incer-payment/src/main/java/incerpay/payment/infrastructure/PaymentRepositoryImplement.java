package incerpay.payment.infrastructure;

import incerpay.payment.domain.Payment;
import incerpay.payment.domain.component.PaymentValidator;
import incerpay.payment.domain.component.PaymentViewer;
import incerpay.payment.domain.component.PaymentRepository;
import incerpay.payment.domain.dto.PayCommand;
import incerpay.payment.domain.dto.PaymentCancelCommand;
import incerpay.payment.domain.dto.PaymentStatusView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImplement implements PaymentRepository, PaymentViewer, PaymentValidator {

    @Override
    public void save(Payment payment) {

    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        return Optional.empty();
    }

    @Override
    public void validate(PayCommand command) {

    }

    @Override
    public void validate(PaymentCancelCommand command) {

    }

    @Override
    public PaymentStatusView readPayment(Payment payment) {
        return null;
    }
}
